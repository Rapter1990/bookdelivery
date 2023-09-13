package com.example.demo.payload.request.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
