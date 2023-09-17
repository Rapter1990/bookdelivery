package com.example.demo.repository;

import com.example.demo.model.RefreshToken;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

/**
 * Repository interface for accessing and managing RefreshToken entities in the database.
 * This interface extends JpaRepository to provide basic CRUD operations for RefreshToken entities.
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Retrieves a refresh token associated with a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return The RefreshToken entity associated with the user, or null if not found.
     */
    RefreshToken findByUserId(Long userId);

    /**
     * Retrieves a refresh token by its token string.
     *
     * @param token The token string of the refresh token.
     * @return An Optional containing the RefreshToken entity if found, or an empty Optional if not found.
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Deletes refresh tokens associated with a specific user.
     *
     * @param user The User entity whose refresh tokens should be deleted.
     * @return The number of refresh tokens deleted.
     */
    @Modifying
    int deleteByUser(User user);
}
