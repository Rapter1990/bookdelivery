package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) representing order item information.
 */
@Getter
@Builder
@EqualsAndHashCode
public class OrderItemDTO {

    private Long id;
    private OrderItemBook book;

    /**
     * Data Transfer Object (DTO) representing book information within an order item.
     */
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
