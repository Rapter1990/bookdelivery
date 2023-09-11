package com.example.demo.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateStockRequest {

    @NotNull
    @Min(value = 0, message = "STOCK AMOUNT MUST AT LEAST BE ONE!")
    private Integer stock;

}
