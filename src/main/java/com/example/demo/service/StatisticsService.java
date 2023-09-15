package com.example.demo.service;

import com.example.demo.dto.OrderReportDTO;
import com.example.demo.payload.request.pagination.PaginationRequest;
import org.springframework.data.domain.Page;


public interface StatisticsService {

    OrderReportDTO getOrderStatisticsByCustomer(Long customerId, PaginationRequest paginationRequest);
    OrderReportDTO getOrderStatistics(PaginationRequest paginationRequest);

}
