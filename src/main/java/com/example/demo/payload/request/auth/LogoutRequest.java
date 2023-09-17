package com.example.demo.payload.request.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request object for user logout.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {

    private String token;
}
