package com.example.demo.service.impl;

import com.example.demo.model.RefreshToken;
import com.example.demo.model.User;
import com.example.demo.payload.payload.JWTResponse;
import com.example.demo.payload.payload.TokenRefreshResponse;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.request.TokenRefreshRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.service.AuthService;
import com.example.demo.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    private final JwtUtils jwtUtils;


    @Override
    public String register(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return "success";
    }

    @Override
    public JWTResponse login(LoginRequest request) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtUtils.generateJwtToken(auth);

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User Not Found"));

        return JWTResponse.builder()
                .email(request.getEmail())
                .token(jwtToken)
                .refreshToken(refreshTokenService.createRefreshToken(user))
                .build();
    }

    @Override
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {

        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));


        if (!refreshTokenService.isRefreshExpired(refreshToken)) {
            CustomUserDetails customUserDetails = new CustomUserDetails(refreshToken.getUser());
            String newToken = jwtUtils.generateJwtToken(customUserDetails);

            return TokenRefreshResponse.builder()
                    .accessToken(newToken)
                    .refreshToken(refreshToken.getToken())
                    .build();
        }

        return null;
    }

    @Override
    public String logout(String token) {

        String authToken = jwtUtils.extractTokenFromHeader(token);

        if (jwtUtils.validateJwtToken(authToken)) {
            Long id = jwtUtils.getIdFromToken(authToken);

            refreshTokenService.deleteByUserId(id);

            return "success";
        }

        return "failed";
    }
}
