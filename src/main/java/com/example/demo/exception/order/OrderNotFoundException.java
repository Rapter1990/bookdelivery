package com.example.demo.exception.order;

import com.example.demo.exception.NotFoundException;

import java.io.Serial;

public class OrderNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = 649871719059363510L;

    private static final String DEFAULT_MESSAGE =
            "Order Not Found!";

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
