package com.example.demo.security.jwt;

import com.example.demo.model.enums.TokenClaims;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.util.Identity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
@Log4j2
public class JwtUtils {


    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expireMs}")
    private int jwtExpirationMs;

    private final Identity identity;

    public JwtUtils(Identity identity) {
        this.identity = identity;
    }


    public String generateJwtToken(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Map<String, Object> claims = userDetails.getClaims();
        return createToken(claims, userDetails.getUsername());
    }

    public String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtToken(CustomUserDetails customUserDetails) {
        Map<String, Object> claims = customUserDetails.getClaims();
        claims.put(TokenClaims.ID.getValue(), customUserDetails.getId());
        return createToken(claims, customUserDetails.getUsername());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getIdFromToken(String token){
        String idValue = extractClaims(token).get(TokenClaims.ID.getValue()).toString();
        Double doubleValue = Double.parseDouble(idValue);
        return doubleValue.longValue();
    }

    public String getEmailFromToken(String token){
        return extractClaims(token).get(TokenClaims.EMAIL.getValue()).toString();
    }

    public boolean validateJwtToken(String authToken) {

        log.info("JwtUtils | validateJwtToken | authToken: {}", authToken);

        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("JwtUtils | validateJwtToken | Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JwtUtils | validateJwtToken | JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JwtUtils | validateJwtToken | JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JwtUtils | validateJwtToken | JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String extractTokenFromHeader(String authorizationHeader) {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
