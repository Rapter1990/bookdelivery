package com.example.demo.service;

import com.example.demo.payload.response.auth.JWTResponse;
import com.example.demo.payload.response.auth.TokenRefreshResponse;
import com.example.demo.payload.request.auth.LoginRequest;
import com.example.demo.payload.request.auth.SignupRequest;
import com.example.demo.payload.request.auth.TokenRefreshRequest;

public interface AuthService {

    // register
    String register(SignupRequest request);

    // login
    JWTResponse login(LoginRequest request);

    // refresh token
    TokenRefreshResponse refreshToken(TokenRefreshRequest request);

    // logout
    String logout(String token);
}
