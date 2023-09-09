package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long userId);
}
