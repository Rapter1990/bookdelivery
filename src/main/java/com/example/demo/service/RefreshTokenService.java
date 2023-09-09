package com.example.demo.service;

import com.example.demo.model.RefreshToken;
import com.example.demo.model.User;

import java.util.Optional;

public interface RefreshTokenService {

    String createRefreshToken(User user);

    boolean isRefreshExpired(RefreshToken token);

    RefreshToken getByUser(Long userId);

    Optional<RefreshToken> findByToken(String token);

    int deleteByUserId(Long userId);
}
