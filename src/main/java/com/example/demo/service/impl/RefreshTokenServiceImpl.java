package com.example.demo.service.impl;

import com.example.demo.exception.user.UserNotFoundException;
import com.example.demo.model.RefreshToken;
import com.example.demo.model.User;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the {@link RefreshTokenService} interface for managing refresh tokens.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refrEshexpireMs}")
    Long expireSeconds;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;

    /**
     * Creates a new refresh token for the specified user.
     *
     * @param user The user for whom the refresh token is created.
     * @return A string representing the newly created refresh token.
     */
    @Override
    public String createRefreshToken(User user) {

        RefreshToken token = getByUser(user.getId());

        if (token == null) {
            token = new RefreshToken();
            token.setUser(user);
        }

        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusSeconds(expireSeconds).atZone(ZoneOffset.UTC).toLocalDate());
        refreshTokenRepository.save(token);

        return token.getToken();
    }

    /**
     * Checks if a refresh token has expired.
     *
     * @param token The refresh token to check for expiration.
     * @return {@code true} if the token has expired, {@code false} otherwise.
     */
    @Override
    public boolean isRefreshExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(LocalDate.now());
    }

    /**
     * Retrieves the refresh token associated with a user by their unique identifier.
     *
     * @param userId The unique identifier of the user.
     * @return A {@link RefreshToken} representing the refresh token associated with the user.
     */
    @Override
    public RefreshToken getByUser(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    /**
     * Finds a refresh token by its token string.
     *
     * @param token The token string to search for.
     * @return An {@link Optional} containing the {@link RefreshToken} if found, or an empty {@link Optional} if not found.
     */
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Deletes a refresh token associated with a user by their unique identifier.
     *
     * @param userId The unique identifier of the user.
     * @return The number of refresh tokens deleted (typically 0 or 1).
     */
    @Override
    @Transactional
    public int deleteByUserId(Long userId) {

        User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return refreshTokenRepository.deleteByUser(user);
    }
}
