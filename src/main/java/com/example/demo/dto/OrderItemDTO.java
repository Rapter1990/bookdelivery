package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemDTO {

    private Long id;
    private BookDTO book;
}
