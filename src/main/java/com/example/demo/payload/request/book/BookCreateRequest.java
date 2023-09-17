package com.example.demo.payload.request.book;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;


/**
 * Represents a request object for creating a new book.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookCreateRequest {
    @NotBlank
    @Size(min = 10, max = 13)
    private String isbn;

    @NotBlank
    private String name;

    @NotBlank
    private String authorFullName;

    @Min(value = 0)
    private Integer stock;

    @NotNull
    private BigDecimal price;

}
