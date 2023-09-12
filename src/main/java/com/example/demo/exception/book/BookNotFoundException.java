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

    public BookNotFoundException(String message){
        super(DEFAULT_MESSAGE+" "+message);
    }

    public BookNotFoundException(){
        super(DEFAULT_MESSAGE);
    }

}
