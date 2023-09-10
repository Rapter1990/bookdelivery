package com.example.demo.controller;

import com.example.demo.base.BaseControllerTest;
import com.example.demo.model.enums.Role;
import com.example.demo.payload.payload.JWTResponse;
import com.example.demo.payload.payload.TokenRefreshResponse;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.request.TokenRefreshRequest;
import com.example.demo.service.AuthService;
import com.example.demo.utils.MockJwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends BaseControllerTest {

    @MockBean
    private AuthService authService;


    @Test
    void register_ReturnSuccess() throws Exception {

        // given
        SignupRequest request = SignupRequest.builder()
                .fullName("customer_fullname")
                .password("customer_password")
                .username("customer_1")
                .email("customer@bookdelivery.com")
                .role(Role.ROLE_CUSTOMER)
                .build();

        when(authService.register(request)).thenReturn("success");

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    void login_ReturnSuccess() throws Exception {

        // given
        LoginRequest request = LoginRequest.builder()
                .email("customer@bookdelivery.com")
                .password("customer_password")
                .build();

        JWTResponse mockResponse = JWTResponse.builder()
                .email(request.getEmail())
                .token("mockedToken")
                .refreshToken("mockedRefreshToken")
                .build();

        // when
        when(authService.login(request)).thenReturn(mockResponse);

        // then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void refreshToken_ReturnSuccess() throws Exception {

        // given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("validRefreshToken")
                .build();

        TokenRefreshResponse mockResponse = TokenRefreshResponse.builder()
                .accessToken("newMockedToken")
                .refreshToken("validRefreshToken")
                .build();


        String mockBearerToken = MockJwtTokenProvider.createMockJwtTokenForCustomer();

        // when
        when(authService.refreshToken(request)).thenReturn(mockResponse);

        // then
        mockMvc.perform(post("/api/v1/auth/refreshtoken")
                        .header("Authorization", mockBearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void logout_ReturnSuccess() throws Exception {

        // Given
        String mockBearerToken = MockJwtTokenProvider.createMockJwtTokenForCustomer();

        // When
        when(authService.logout(mockBearerToken)).thenReturn("success");

        // Then
        mockMvc.perform(post("/api/v1/auth/logout")
                        .header("Authorization", mockBearerToken))
                .andExpect(status().isOk());

        verify(authService).logout(mockBearerToken);

    }
}