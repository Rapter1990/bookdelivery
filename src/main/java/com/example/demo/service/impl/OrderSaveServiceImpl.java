package com.example.demo.service.impl;

import com.example.demo.dto.OrderDTO;
import com.example.demo.payload.request.order.CreateOrderRequest;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.OrderSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderSaveServiceImpl implements OrderSaveService {

    @Override
    public OrderDTO createOrder(CreateOrderRequest createOrderRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        // Get Customer Info

        // add OrderItem to OrderItem -> Call OrderItem Service

        // convert OrderItem to OrderDTO

        // Save Order

        // return Order to OrderDTO

        return null;
    }
}
