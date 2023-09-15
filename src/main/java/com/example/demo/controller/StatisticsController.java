package com.example.demo.controller;

import com.example.demo.dto.OrderReportDTO;
import com.example.demo.model.mapper.order.OrderReportMapper;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.payload.response.CustomResponse;
import com.example.demo.payload.response.order.OrderReportResponse;
import com.example.demo.service.StatisticsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
    public CustomResponse<OrderReportResponse> getOrderStatisticsByCustomer(
            @PathVariable Long customerId,
            @RequestBody PaginationRequest paginationRequest
    ) {

        OrderReportDTO orderReportDtosByCustomer = statisticsService.getOrderStatisticsByCustomer(customerId,paginationRequest);
        OrderReportResponse orderReportResponse = OrderReportMapper.toOrderReportResponse(orderReportDtosByCustomer);
        return CustomResponse.ok(orderReportResponse);

    }

    @GetMapping("/allstatistics")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
    public CustomResponse<OrderReportResponse> getOrderStatistics(@RequestBody PaginationRequest paginationRequest) {

        OrderReportDTO orderReportAllDtos = statisticsService.getOrderStatistics(paginationRequest);
        OrderReportResponse orderReportResponse = OrderReportMapper.toOrderReportResponse(orderReportAllDtos);
        return CustomResponse.ok(orderReportResponse);
    }

}
