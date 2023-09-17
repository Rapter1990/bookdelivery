package com.example.demo.payload.request.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents a request object for updating the stock of an existing book.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateStockRequest {

    @NotNull
    @Min(value = 0, message = "STOCK AMOUNT MUST AT LEAST BE ONE!")
    private Integer stock;

}
