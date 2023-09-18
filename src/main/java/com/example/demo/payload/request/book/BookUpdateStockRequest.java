package com.example.demo.payload.request.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request object for updating the stock of an existing book.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateStockRequest {

    @NotNull
    @Min(value = 0, message = "STOCK AMOUNT MUST AT LEAST BE ONE!")
    private Integer stock;

}
