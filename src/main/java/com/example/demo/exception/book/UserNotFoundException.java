package com.example.demo.exception.book;

import com.example.demo.exception.NotFoundException;

import java.io.Serial;

/**
 * Custom exception class for cases where a user is not found.
 */
public class UserNotFoundException extends NotFoundException {


    @Serial
    private static final long serialVersionUID = -5053937719818804066L;

    private static final String DEFAULT_MESSAGE =
            "User Not Found!";

    private static final String MESSAGE_TEMPLATE =
            "User Not Found with ID: ";

    /**
     * Constructs a UserNotFoundException with a specific user ID.
     *
     * @param id The ID of the user that was not found.
     */
    public UserNotFoundException(String id) {
        super(MESSAGE_TEMPLATE.concat(id));
    }

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
