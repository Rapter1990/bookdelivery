package com.example.demo.service;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.payload.request.order.OrderItemRequest;

/**
 * This interface defines a service for managing order items within an order.
 */
public interface OrderItemService {

    /**
     * Creates a new order item based on the provided order item request.
     *
     * @param orderDetailRequest The request containing order item information to be used for creation.
     * @return An {@link OrderItemDTO} representing the newly created order item.
     */
    OrderItemDTO createOrderItem(OrderItemRequest orderDetailRequest);

}
