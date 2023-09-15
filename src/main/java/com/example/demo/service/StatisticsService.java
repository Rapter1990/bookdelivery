package com.example.demo.service;

import com.example.demo.dto.OrderReportDTO;
import org.springframework.data.domain.Page;


public interface StatisticsService {

    Page<OrderReportDTO> getOrderStatisticsByCustomer(Long customerId);
    Page<OrderReportDTO> getOrderStatistics();

}
