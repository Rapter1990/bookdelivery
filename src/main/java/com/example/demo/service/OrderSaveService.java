package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.payload.request.order.CreateOrderRequest;

public interface OrderSaveService {

    OrderDTO createOrder(CreateOrderRequest createOrderRequest);


}
