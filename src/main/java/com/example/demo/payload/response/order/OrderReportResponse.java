package com.example.demo.payload.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReportResponse {

    private String month;
    private Integer year;
    private Integer totalOrderCount;
    private Integer totalBookCount;
    private BigDecimal totalPrice;

}
