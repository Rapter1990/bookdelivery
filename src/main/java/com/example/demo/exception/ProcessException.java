package com.example.demo.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * The abstract base class for exceptions indicating a failure in a specific process or operation.
 *
 * <p>
 * This class extends {@code RuntimeException} and provides a custom HTTP status code ({@code HttpStatus.BAD_REQUEST})
 * to represent the error condition.
 * </p>
 *
 * <p>
 * Subclasses of this class should be created to represent specific processes or operations that may fail, providing
 * meaningful error messages and handling the exception appropriately in the application's error handling logic.
 * </p>
 *
 * @serial -4150198597761563298L
 * @see RuntimeException
 * @see org.springframework.http.HttpStatus
 */
public abstract class ProcessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4150198597761563298L;

    public static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * Constructs a {@code ProcessException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    protected ProcessException(String message) {
        super(message);
    }
}
