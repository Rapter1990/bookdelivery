package com.example.demo.service;

import com.example.demo.model.RefreshToken;
import com.example.demo.model.User;

import java.util.Optional;

/**
 * This interface defines a service for managing refresh tokens.
 */
public interface RefreshTokenService {

    /**
     * Creates a new refresh token for the specified user.
     *
     * @param user The user for whom the refresh token is created.
     * @return A string representing the newly created refresh token.
     */
    String createRefreshToken(User user);

    /**
     * Checks if a refresh token has expired.
     *
     * @param token The refresh token to check for expiration.
     * @return {@code true} if the token has expired, {@code false} otherwise.
     */
    boolean isRefreshExpired(RefreshToken token);

    /**
     * Retrieves the refresh token associated with a user by their unique identifier.
     *
     * @param userId The unique identifier of the user.
     * @return A {@link RefreshToken} representing the refresh token associated with the user.
     */
    RefreshToken getByUser(Long userId);

    /**
     * Finds a refresh token by its token string.
     *
     * @param token The token string to search for.
     * @return An {@link Optional} containing the {@link RefreshToken} if found, or an empty {@link Optional} if not found.
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Deletes a refresh token associated with a user by their unique identifier.
     *
     * @param userId The unique identifier of the user.
     * @return The number of refresh tokens deleted (typically 0 or 1).
     */
    int deleteByUserId(Long userId);

}
