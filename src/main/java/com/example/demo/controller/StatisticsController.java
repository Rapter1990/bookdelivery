package com.example.demo.controller;

import com.example.demo.dto.OrderReportDTO;
import com.example.demo.model.enums.Role;
import com.example.demo.model.mapper.order.OrderReportMapper;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.payload.response.CustomResponse;
import com.example.demo.payload.response.order.OrderReportResponse;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.StatisticsService;
import com.example.demo.util.Identity;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class StatisticsController {

    private final StatisticsService statisticsService;

    private final Identity identity;

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
    public CustomResponse<OrderReportResponse> getOrderStatisticsByCustomer(
            @PathVariable Long customerId,
            @RequestBody PaginationRequest paginationRequest
    ) {

        CustomUserDetails customUserDetails = identity.getCustomerUserDetails();

        if( (customUserDetails.getId().equals(customerId) &&
                customUserDetails.getUser().getRole().equals(Role.ROLE_CUSTOMER)
                ) || customUserDetails.getUser().getRole().equals(Role.ROLE_ADMIN)
        ){
            OrderReportDTO orderReportDtosByCustomer = statisticsService.getOrderStatisticsByCustomer(customerId,paginationRequest);
            OrderReportResponse orderReportResponse = OrderReportMapper.toOrderReportResponse(orderReportDtosByCustomer);
            return CustomResponse.ok(orderReportResponse);
        }

        throw new AccessDeniedException("You cannot access order statistics");
    }

    @GetMapping("/allstatistics")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public CustomResponse<OrderReportResponse> getOrderStatistics(@RequestBody PaginationRequest paginationRequest) {

        OrderReportDTO orderReportAllDtos = statisticsService.getOrderStatistics(paginationRequest);
        OrderReportResponse orderReportResponse = OrderReportMapper.toOrderReportResponse(orderReportAllDtos);
        return CustomResponse.ok(orderReportResponse);

    }

}
