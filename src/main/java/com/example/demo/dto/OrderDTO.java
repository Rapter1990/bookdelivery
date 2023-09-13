package com.example.demo.dto;

import com.example.demo.model.OrderItem;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
public class OrderDTO {

    private Long id;
    private UserDTO user;
    private LocalDateTime createdAt;
    private Set<OrderItemDTO> orderItems;

}
