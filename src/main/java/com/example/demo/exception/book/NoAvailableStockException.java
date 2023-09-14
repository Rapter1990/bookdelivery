package com.example.demo.exception.book;

import java.io.Serial;

public class NoAvailableStockException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5669253377318124296L;

    private static final String DEFAULT_MESSAGE =
            "No available stock!";

    private static final String MESSAGE_TEMPLATE =
            "No book found with No available stock for given amount : ";

    public NoAvailableStockException(String id) {
        super(MESSAGE_TEMPLATE.concat(id));
    }

    public NoAvailableStockException() {
        super(DEFAULT_MESSAGE);
    }
}
