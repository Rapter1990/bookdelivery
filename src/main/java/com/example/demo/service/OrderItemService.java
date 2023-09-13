package com.example.demo.service;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.payload.request.order.OrderItemRequest;

public interface OrderItemService {

    // create Order Item
    OrderItemDTO createOrderItem(OrderItemRequest orderDetailRequest);
}
