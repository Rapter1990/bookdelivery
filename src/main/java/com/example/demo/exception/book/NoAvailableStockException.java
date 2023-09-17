package com.example.demo.exception.book;

import java.io.Serial;

public class NoAvailableStockException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5669253377318124296L;

    private static final String DEFAULT_MESSAGE =
            "No available stock!";

    private static final String MESSAGE_TEMPLATE =
            "No available stock for the given amount : ";

    public NoAvailableStockException(int amount) {
        super(MESSAGE_TEMPLATE.concat(String.valueOf(amount)));
    }

    public NoAvailableStockException() {
        super(DEFAULT_MESSAGE);
    }
}
