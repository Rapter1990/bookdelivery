package com.example.demo.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing order information.
 */
@Getter
@Builder
@EqualsAndHashCode
public class OrderDTO {

    private Long id;
    private UserDTO user;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> orderItems;

}
