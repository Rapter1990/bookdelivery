package com.example.demo.payload.response.book;

import lombok.*;

import java.math.BigDecimal;

/**
 * Represents a response object for book creation, containing book details.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookCreatedResponse {
    private String id;
    private String isbn;
    private String name;
    private String authorFullName;
    private Integer stock;
    private BigDecimal price;
}
