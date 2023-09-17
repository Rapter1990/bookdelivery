package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceTest;
import com.example.demo.model.RefreshToken;
import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import com.example.demo.payload.request.auth.LoginRequest;
import com.example.demo.payload.request.auth.SignupRequest;
import com.example.demo.payload.request.auth.TokenRefreshRequest;
import com.example.demo.payload.response.auth.JWTResponse;
import com.example.demo.payload.response.auth.TokenRefreshResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.service.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceImplTest extends BaseServiceTest {


    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void givenSignUpRequest_WhenCustomerRole_ReturnSuccess() {

        // given
        SignupRequest request = SignupRequest.builder()
                .fullName("customer_fullname")
                .password("customer_password")
                .username("customer_1")
                .email("customer@bookdelivery.com")
                .role(Role.ROLE_CUSTOMER)
                .build();

        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        // when
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // then
        String result = authService.register(request);

        assertEquals("success", result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void givenSignUpRequest_WhenCustomerRoleAndEmailAlreadyExists_ReturnException() {

        // given
        SignupRequest request = SignupRequest.builder()
                .fullName("customer_fullname")
                .password("customer_password")
                .username("customer_1")
                .email("customer@bookdelivery.com")
                .role(Role.ROLE_CUSTOMER)
                .build();

        // when
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // then
        assertThrows(Exception.class, () -> authService.register(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void givenLoginRequest_WhenCustomerRole_ReturnSuccess() {

        // given
        LoginRequest request = LoginRequest.builder()
                .email("customer@bookdelivery.com")
                .password("customer_password")
                .build();

        User mockUser = User.builder()
                .email(request.getEmail())
                .fullName("Test User")
                .username("testuser")
                .password("hashedPassword")
                .role(Role.ROLE_CUSTOMER)
                .build();

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());

        // when
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        when(jwtUtils.generateJwtToken(mockAuthentication)).thenReturn("mockedToken");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockUser));
        when(refreshTokenService.createRefreshToken(any(User.class)))
                .thenReturn("actualRefreshToken");

        // then
        JWTResponse jwtResponse = authService.login(request);

        assertNotNull(jwtResponse);
        assertEquals(request.getEmail(), jwtResponse.getEmail());
        assertEquals("mockedToken", jwtResponse.getToken());
        assertEquals("actualRefreshToken", jwtResponse.getRefreshToken());

    }

    @Test
    void givenLoginRequest_WhenCustomerRole_ReturnRuntimeException() {

        // given
        LoginRequest request = LoginRequest.builder()
                .email("customer@bookdelivery.com")
                .password("customer_password")
                .build();

        // when
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        // then
        assertThrows(RuntimeException.class, () -> authService.login(request));

    }

    @Test
    void givenTokenRefreshRequest_WhenCustomerRole_ReturnSuccess() {

        // given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("validRefreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("validRefreshToken")
                .user(User.builder().id(1L).build())
                .build();

        // when
        when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.isRefreshExpired(refreshToken)).thenReturn(false);
        when(jwtUtils.generateJwtToken(any(CustomUserDetails.class))).thenReturn("newMockedToken");

        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request);

        assertNotNull(tokenRefreshResponse);
        assertEquals("newMockedToken", tokenRefreshResponse.getAccessToken());
        assertEquals("validRefreshToken", tokenRefreshResponse.getRefreshToken());

    }

    @Test
    void givenTokenRefreshRequest_WhenCustomerRole_ReturnRefreshTokenNotFound() {

        // given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("invalidRefreshToken")
                .build();

        // when
        when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(Exception.class, () -> authService.refreshToken(request));
    }

    @Test
    void givenTokenRefreshRequest_WhenCustomerRole_ReturnRefreshTokenExpired() {

        // given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("expiredRefreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("validRefreshToken")
                .user(User.builder().id(1L).build())
                .build();

        // when
        when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.isRefreshExpired(refreshToken)).thenReturn(true);

        // then
        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request);

        assertNull(tokenRefreshResponse);
    }

    @Test
    void givenValidAccessToken_WhenCustomerRole_ReturnLogoutSuccess() {

        // Given
        String token = "validAuthToken";
        Long userId = 1L;

        // When
        when(jwtUtils.extractTokenFromHeader(token)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(jwtUtils.getIdFromToken(token)).thenReturn(userId);

        // Then
        String result = authService.logout(token);

        assertEquals("success", result);
        verify(refreshTokenService).deleteByUserId(userId);
    }

    @Test
    void givenInvalidAccessToken_WhenCustomerRole_ReturnLogoutFailed() {
        // Given
        String token = "invalidAuthToken";

        when(jwtUtils.extractTokenFromHeader(token)).thenReturn(null); // Invalid token

        // When
        String result = authService.logout(token);

        // Then
        assertEquals("failed", result);
        verify(refreshTokenService, never()).deleteByUserId(anyLong());
    }

    @Test
    void givenInvalidAccessToken_WhenCustomerRole_ReturnLogoutInvalidJwtToken() {
        // Given
        String token = "invalidAuthToken";

        when(jwtUtils.extractTokenFromHeader(token)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(false);

        // When
        String result = authService.logout(token);

        // Then
        assertEquals("failed", result);
        verify(refreshTokenService, never()).deleteByUserId(anyLong());

    }

    @Test
    void givenSignUpRequest_WhenAdminRole_ReturnSuccess() {

        // given
        SignupRequest request = SignupRequest.builder()
                .fullName("admin_fullname")
                .password("admin_password")
                .username("admin_1")
                .email("admin@bookdelivery.com")
                .role(Role.ROLE_ADMIN)
                .build();

        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        // when
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // then
        String result = authService.register(request);

        assertEquals("success", result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void givenSignUpRequest_WhenAdminRoleAndEmailAlreadyExists_ReturnException() {

        // given
        SignupRequest request = SignupRequest.builder()
                .fullName("admin_fullname")
                .password("admin_password")
                .username("admin_1")
                .email("admin@bookdelivery.com")
                .role(Role.ROLE_ADMIN)
                .build();

        // when
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // then
        assertThrows(Exception.class, () -> authService.register(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void givenLoginRequest_WhenWhenAdminRole_ReturnSuccess() {

        // given
        LoginRequest request = LoginRequest.builder()
                .email("admin@bookdelivery.com")
                .password("admin_password")
                .build();

        User mockUser = User.builder()
                .email(request.getEmail())
                .fullName("Test User")
                .username("testuser")
                .password("hashedPassword")
                .role(Role.ROLE_CUSTOMER)
                .build();

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());

        // when
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        when(jwtUtils.generateJwtToken(mockAuthentication)).thenReturn("mockedToken");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockUser));
        when(refreshTokenService.createRefreshToken(any(User.class)))
                .thenReturn("actualRefreshToken");

        // then
        JWTResponse jwtResponse = authService.login(request);

        assertNotNull(jwtResponse);
        assertEquals(request.getEmail(), jwtResponse.getEmail());
        assertEquals("mockedToken", jwtResponse.getToken());
        assertEquals("actualRefreshToken", jwtResponse.getRefreshToken());

    }

    @Test
    void givenLoginRequest_WhenAdminRole_ReturnRuntimeException() {

        // given
        LoginRequest request = LoginRequest.builder()
                .email("admin@bookdelivery.com")
                .password("admin_password")
                .build();

        // when
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        // then
        assertThrows(RuntimeException.class, () -> authService.login(request));

    }

    @Test
    void givenTokenRefreshRequest_WhenAdminRole_ReturnSuccess() {

        // given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("validRefreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("validRefreshToken")
                .user(User.builder().id(1L).build())
                .build();

        // when
        when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.isRefreshExpired(refreshToken)).thenReturn(false);
        when(jwtUtils.generateJwtToken(any(CustomUserDetails.class))).thenReturn("newMockedToken");

        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request);

        assertNotNull(tokenRefreshResponse);
        assertEquals("newMockedToken", tokenRefreshResponse.getAccessToken());
        assertEquals("validRefreshToken", tokenRefreshResponse.getRefreshToken());

    }

    @Test
    void givenTokenRefreshRequest_WhenAdminRole_ReturnRefreshTokenNotFound() {

        // given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("invalidRefreshToken")
                .build();

        // when
        when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(Exception.class, () -> authService.refreshToken(request));
    }

    @Test
    void givenTokenRefreshRequest_WhenAdminRole_ReturnRefreshTokenExpired() {

        // given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("expiredRefreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("validRefreshToken")
                .user(User.builder().id(1L).build())
                .build();

        // when
        when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.isRefreshExpired(refreshToken)).thenReturn(true);

        // then
        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request);

        assertNull(tokenRefreshResponse);
    }

    @Test
    void givenValidAccessToken_WhenAdminRole_ReturnLogoutSuccess() {

        // Given
        String token = "validAuthToken";
        Long userId = 1L;

        // When
        when(jwtUtils.extractTokenFromHeader(token)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(jwtUtils.getIdFromToken(token)).thenReturn(userId);

        // Then
        String result = authService.logout(token);

        assertEquals("success", result);
        verify(refreshTokenService).deleteByUserId(userId);
    }

    @Test
    void givenInvalidAccessToken_WhenAdminRole_ReturnLogoutFailed() {
        // Given
        String token = "invalidAuthToken";

        when(jwtUtils.extractTokenFromHeader(token)).thenReturn(null); // Invalid token

        // When
        String result = authService.logout(token);

        // Then
        assertEquals("failed", result);
        verify(refreshTokenService, never()).deleteByUserId(anyLong());
    }

    @Test
    void givenInvalidAccessToken_WhenAdminRole_ReturnLogoutInvalidJwtToken() {
        // Given
        String token = "invalidAuthToken";

        when(jwtUtils.extractTokenFromHeader(token)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(false);

        // When
        String result = authService.logout(token);

        // Then
        assertEquals("failed", result);
        verify(refreshTokenService, never()).deleteByUserId(anyLong());

    }
}