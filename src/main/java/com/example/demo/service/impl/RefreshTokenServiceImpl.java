package com.example.demo.service.impl;

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

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refrEshexpireMs}")
    Long expireSeconds;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;


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

    @Override
    public boolean isRefreshExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(LocalDate.now());
    }

    @Override
    public RefreshToken getByUser(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public int deleteByUserId(Long userId) {

        User user = userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return refreshTokenRepository.deleteByUser(user);
    }
}
