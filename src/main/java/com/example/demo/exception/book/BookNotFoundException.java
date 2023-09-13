package com.example.demo.exception.book;

import java.io.Serial;
import com.example.demo.model.Book;

/**
 * Thrown when the specified {@link Book} is not found.
 */
public class BookNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 6756200070542203753L;

    private static final String DEFAULT_MESSAGE =
            "The specified Book is not found!";

    private static final String MESSAGE_TEMPLATE =
            "No book found with ID: ";

    public BookNotFoundException(String id){
        super(MESSAGE_TEMPLATE.concat(id));
    }

    public BookNotFoundException(){
        super(DEFAULT_MESSAGE);
    }

}
