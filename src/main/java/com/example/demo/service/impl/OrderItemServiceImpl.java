package com.example.demo.service.impl;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.payload.request.order.OrderItemRequest;
import com.example.demo.service.OrderItemService;

public class OrderItemServiceImpl implements OrderItemService {


    // Optimistic Lock @Retry or @Retryable
    @Override
    public OrderItemDTO createOrderItem(OrderItemRequest orderDetailRequest) {

        // create OrderDTO

        // assign values from orderDetailRequest to OrderDTO

        // get BookDTO from bookId of orderDetailRequest

        // check if the stock of book is available

        // calculate price (amount)

        // update stock value of the book from BookService -> BookService

        // return OrderItemDTO

        return null;
    }
}
