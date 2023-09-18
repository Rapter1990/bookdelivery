package com.example.demo.exception.token;

import com.example.demo.exception.NotFoundException;

import java.io.Serial;

public class RefreshTokenNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = -3287463750350903132L;

    private static final String DEFAULT_MESSAGE =
            "The specified Refresh Token is not found!";

    public RefreshTokenNotFoundException(String message) {
        super(message);
    }

    public RefreshTokenNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
