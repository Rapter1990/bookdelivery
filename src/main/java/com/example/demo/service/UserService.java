package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Optional;

/**
 * This interface defines a service for managing user-related operations.
 */
public interface UserService {

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return An {@link Optional} containing the {@link User} if found, or an empty {@link Optional} if not found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their unique identifier.
     *
     * @param userId The unique identifier of the user.
     * @return An {@link Optional} containing the {@link User} if found, or an empty {@link Optional} if not found.
     */
    Optional<User> findById(Long userId);

    /**
     * Finds a user by their email address.
     *
     * @param email The email address to search for.
     * @return An {@link Optional} containing the {@link User} if found, or an empty {@link Optional} if not found.
     */
    Optional<User> findByEmail(String email);

}
