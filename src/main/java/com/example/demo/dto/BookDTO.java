package com.example.demo.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) representing book information.
 */
@Getter
@Builder
@EqualsAndHashCode
public class BookDTO {

    private String id;
    private String isbn;
    private String name;
    private String authorFullName;
    private BigDecimal price;
    private Integer stock;
}
