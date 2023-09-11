package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
public class MockJwtTokenProvider {


    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expireMs}")
    private int EXPIRATION_TIME_MS;

    public String createMockJwtTokenForCustomer() {

        Claims claims = Jwts.claims();
                //.setSubject("customer_1"); // Set the username as the subject
                //.setIssuedAt(new Date())
                //.setExpiration(new Date(System.currentTimeMillis() + MockJwtTokenProvider.EXPIRATION_TIME_MS));

        claims.put("id", 1);
        claims.put("username", "customer_1");
        claims.put("roles", Collections.singletonList("ROLE_CUSTOMER"));
        claims.put("userFullName", "customer_fullname");
        claims.put("email", "customer@bookdelivery.com");

        Date expirationDate = new Date(new Date().getTime() + EXPIRATION_TIME_MS);

        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

        // Build the JWT token with the provided claims
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject("customer_1")
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return "Bearer " + token;
    }

}
