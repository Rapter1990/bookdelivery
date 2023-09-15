package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
public class OrderItemDTO {

    private Long id;
    private OrderItemBook book;

    @Getter
    @Builder
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class OrderItemBook {
        private String id;
        private String isbn;
        private String name;
        private String authorFullName;
        private BigDecimal price;
    }

}
