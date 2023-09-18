package com.example.demo.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * The abstract base class for exceptions indicating that an operation cannot be performed because
 * the resource already exists.
 *
 * <p>
 * This class extends {@code RuntimeException} and provides a custom HTTP status code ({@code HttpStatus.BAD_REQUEST})
 * to represent the error condition.
 * </p>
 *
 * <p>
 * Subclasses of this class should be created to represent specific resource types and situations where the
 * "already exists" condition may occur. They should provide meaningful error messages and handle the exception
 * appropriately in the application's error handling logic.
 * </p>
 *
 * @serial -7986483342718835320L
 * @see RuntimeException
 * @see org.springframework.http.HttpStatus
 */
public abstract class AlreadyException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7986483342718835320L;

    public static final HttpStatus STATUS = HttpStatus.CONFLICT;

    /**
     * Constructs an {@code AlreadyException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    protected AlreadyException(String message) {
        super(message);
    }

}
