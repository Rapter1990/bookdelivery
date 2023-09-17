package com.example.demo.payload.response.book;

import lombok.*;

import java.math.BigDecimal;

/**
 * Represents a response object for updated book details.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdatedResponse {
    private String id;
    private String isbn;
    private String name;
    private String authorFullName;
    private Integer stock;
    private BigDecimal price;
}
