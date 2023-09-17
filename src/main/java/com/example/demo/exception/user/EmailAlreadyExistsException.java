package com.example.demo.exception.user;

import com.example.demo.exception.AlreadyException;
import com.example.demo.model.User;

import java.io.Serial;

/**
 * Thrown when the specified {@link User#getEmail()} already exists.
 */
public class EmailAlreadyExistsException extends AlreadyException {
    @Serial
    private static final long serialVersionUID = -2709807490567608538L;

    private static final String DEFAULT_MESSAGE =
            "The specified email already exists";

    private static final String MESSAGE_TEMPLATE =
            "Email already exists: ";

    /**
     * Constructs an {@code AlreadyException} with the specified email.
     *
     * @param email The email.
     */
    public EmailAlreadyExistsException(String email) {
        super(MESSAGE_TEMPLATE.concat(email));
    }

    public EmailAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }
}
