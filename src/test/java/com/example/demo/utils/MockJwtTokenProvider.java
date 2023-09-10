package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;

public class MockJwtTokenProvider {


    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public static final long EXPIRATION_TIME_MS = 3600000; // 1 hour

    public static String createMockJwtTokenForCustomer() {

        Claims claims = Jwts.claims()
                .setSubject("customer_1") // Set the username as the subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + MockJwtTokenProvider.EXPIRATION_TIME_MS));

        claims.put("roles", Collections.singletonList("ROLE_CUSTOMER"));
        claims.put("userFullName", "customer_fullname");
        claims.put("id", 1);
        claims.put("email", "customer@bookdelivery.com");

        // Create a secret key from your actual secret key string
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        // Build the JWT token with the provided claims
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        return "Bearer " + token;
    }
}
