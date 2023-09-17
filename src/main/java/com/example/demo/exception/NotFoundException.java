package com.example.demo.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * An abstract base class for exceptions representing a resource not found error (HTTP 404 - Not Found).
 * Extend this class to create specific not found exceptions for different resource types.
 */
public abstract class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -117658738198299825L;

    public static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    /**
     * Constructs a new `NotFoundException` with the specified error message.
     *
     * @param message A message describing the not found error.
     */
    protected NotFoundException(String message) {
        super(message);
    }

}
