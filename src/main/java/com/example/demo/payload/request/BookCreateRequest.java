package com.example.demo.payload.request;



import com.example.demo.model.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * Used for create new {@link Book}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {
    @NotBlank
    @Size(min = 10,max = 13)
    private String isbn;

    @NotBlank
    private String name;

    @NotBlank
    private String authorFullName;

    @NotBlank
    @Size(min = 0, max = Integer.MAX_VALUE)
    private Integer stock;

    @NotBlank
    private BigDecimal price;

}
