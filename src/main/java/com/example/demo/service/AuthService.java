package com.example.demo.service;

import com.example.demo.payload.payload.JWTResponse;
import com.example.demo.payload.payload.TokenRefreshResponse;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.request.TokenRefreshRequest;

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
