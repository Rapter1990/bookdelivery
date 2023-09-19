package com.example.demo.payload.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents a response object for generating order reports, containing report data.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReportResponse {

    private String month;
    private Integer year;
    private Long totalOrderCount;
    private Long totalBookCount;
    private BigDecimal totalPrice;

}
