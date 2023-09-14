package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderItemDTO {

    private Long id;
    private OrderItemBook book;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class OrderItemBook {
        private String id;
        private String isbn;
        private String name;
        private String authorFullName;
        private BigDecimal price;
    }

}
