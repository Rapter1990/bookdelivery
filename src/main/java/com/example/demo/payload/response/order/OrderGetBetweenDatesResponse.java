package com.example.demo.payload.response.order;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.UserDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetBetweenDatesResponse {

    private Long id;
    private UserDTO user;
    private LocalDateTime createdAt;
    private Set<OrderItemDTO> orderItems;

}
