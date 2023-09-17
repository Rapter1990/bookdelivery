package com.example.demo.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing standard JWT claims.
 */
@Getter
@RequiredArgsConstructor
public enum TokenClaims {

    JWT_ID("jti"),
    TYPE("typ"),
    SUBJECT("sub"),
    ROLES("roles"),
    ID("id"),
    USERNAME("username"),
    EMAIL("email"),
    USER_FULL_NAME("userFullName"),
    ISSUED_AT("iat"),
    EXPIRES_AT("exp");

    private final String value;

}
