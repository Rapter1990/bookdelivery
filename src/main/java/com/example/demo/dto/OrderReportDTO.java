package com.example.demo.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) representing order report information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReportDTO {

    private String month;
    private Integer year;
    private Long totalOrderCount;
    private Long totalBookCount;
    private BigDecimal totalPrice;

}
