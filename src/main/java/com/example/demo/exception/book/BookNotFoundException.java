package com.example.demo.exception.book;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Book;

import java.io.Serial;

/**
 * Thrown when the specified {@link Book} is not found.
 */
public class BookNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = 6756200070542203753L;

    private static final String DEFAULT_MESSAGE =
            "The specified Book is not found!";

    private static final String MESSAGE_TEMPLATE =
            "No book found with ID: ";

    public BookNotFoundException(String id) {
        super(MESSAGE_TEMPLATE.concat(id));
    }

    public BookNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}
