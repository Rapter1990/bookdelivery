package com.example.demo.service.impl;

import com.example.demo.dto.OrderDTO;
import com.example.demo.payload.request.order.CreateOrderRequest;
import com.example.demo.service.OrderSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderSaveServiceImpl implements OrderSaveService {

    @Override
    public OrderDTO createOrder(CreateOrderRequest createOrderRequest) {
        return null;
    }
}
