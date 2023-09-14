package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceTest;
import com.example.demo.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class OrderSaveServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private OrderSaveServiceImpl orderSaveService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void createOrder() {

    }

}