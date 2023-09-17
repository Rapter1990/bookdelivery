package com.example.demo.security.jwt;

import com.example.demo.model.enums.TokenClaims;
import com.example.demo.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * Utility class for handling JWT (JSON Web Token) operations.
 */
@Component
@Log4j2
public class JwtUtils {


    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expireMs}")
    private int jwtExpirationMs;

    /**
     * Generates a JWT token based on the provided authentication details.
     *
     * @param auth The authentication object containing user details.
     * @return A JWT token as a string.
     */
    public String generateJwtToken(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Map<String, Object> claims = userDetails.getClaims();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Creates a JWT token with the given claims and subject.
     *
     * @param claims  The claims to include in the token.
     * @param subject The subject (typically the username) for the token.
     * @return A JWT token as a string.
     */
    public String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates a JWT token for a CustomUserDetails object, including user-specific claims.
     *
     * @param customUserDetails The CustomUserDetails object representing the user.
     * @return A JWT token as a string.
     */
    public String generateJwtToken(CustomUserDetails customUserDetails) {
        Map<String, Object> claims = customUserDetails.getClaims();
        claims.put(TokenClaims.ID.getValue(), customUserDetails.getId());
        return createToken(claims, customUserDetails.getUsername());
    }

    /**
     * Retrieves the signing key from the JWT secret.
     *
     * @return A Key object representing the signing key.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts the claims (payload) from a JWT token.
     *
     * @param token The JWT token as a string.
     * @return A Claims object containing the token's claims.
     */
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the user's ID from a JWT token.
     *
     * @param token The JWT token as a string.
     * @return The user's ID as a Long.
     */
    public Long getIdFromToken(String token) {
        String idValue = extractClaims(token).get(TokenClaims.ID.getValue()).toString();
        Double doubleValue = Double.parseDouble(idValue);
        return doubleValue.longValue();
    }

    /**
     * Retrieves the user's email from a JWT token.
     *
     * @param token The JWT token as a string.
     * @return The user's email as a string.
     */
    public String getEmailFromToken(String token) {
        return extractClaims(token).get(TokenClaims.EMAIL.getValue()).toString();
    }

    /**
     * Validates the integrity and expiration of a JWT token.
     *
     * @param authToken The JWT token as a string.
     * @return `true` if the token is valid, `false` otherwise.
     */
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

    /**
     * Extracts the token from an "Authorization" header.
     *
     * @param authorizationHeader The "Authorization" header containing the token.
     * @return The token as a string or `null` if not found.
     */
    public String extractTokenFromHeader(String authorizationHeader) {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
