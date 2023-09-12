package com.example.demo.payload.request.book;

import com.example.demo.model.Book;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Used for update {@link Book} entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {
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
