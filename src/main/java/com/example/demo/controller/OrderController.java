package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.mapper.order.OrderMapper;
import com.example.demo.payload.response.CustomResponse;
import com.example.demo.payload.response.order.OrderGetResponse;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public CustomResponse<OrderGetResponse> getOrderById(@PathVariable Long orderId) {
        final OrderDTO orderDTO = orderService.findOrderById(orderId);
        final OrderGetResponse response = OrderMapper.toGetResponse(orderDTO);

        return CustomResponse.ok(response);
    }

    // Implement OrderService
    // Can Create OrderSaveService for Create Order

}
