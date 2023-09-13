package com.example.demo.payload.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReportResponse {

    private String month;
    private Integer year;
    private Integer totalOrderCount;
    private Integer totalBookCount;
    private Double totalPrice;

}
