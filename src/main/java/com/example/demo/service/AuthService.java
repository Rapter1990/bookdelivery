package com.example.demo.service;

import com.example.demo.payload.payload.JWTResponse;
import com.example.demo.payload.payload.TokenRefreshResponse;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.LogoutRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.request.TokenRefreshRequest;

public interface AuthService {

    // register
    String register(SignupRequest request) throws Exception;

    // login
    JWTResponse login(LoginRequest request);

    // refresh token
    TokenRefreshResponse refreshToken(TokenRefreshRequest request) throws Exception;

    // logout
    String logout(LogoutRequest request);
}
