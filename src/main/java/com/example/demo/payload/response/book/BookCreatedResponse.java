package com.example.demo.payload.response.book;

import com.example.demo.model.Book;
import lombok.*;

import java.math.BigDecimal;

/**
 * Represents created {@link Book} entity in response.
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
