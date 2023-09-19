package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceTest;
import com.example.demo.builder.UserBuilder;
import com.example.demo.dto.OrderReportDTO;
import com.example.demo.model.User;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.repository.OrderRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.util.Identity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StatisticsServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Identity identity;

    @Test
    void givenCustomerIdAndPaginationRequest_whenCustomerRoleAndValidCustomerId_returnPageOfOrderReportDTO() {

        // Given
        Long userId = 5L;
        User customer = new UserBuilder().customer().withId(userId).build();

        PaginationRequest paginationRequest = PaginationRequest.builder()
                .page(1)
                .size(10)
                .build();

        OrderReportDTO orderReportDTO = OrderReportDTO.builder()
                .month(Month.SEPTEMBER.name())
                .year(2023)
                .totalOrderCount(100L)
                .totalBookCount(200L)
                .totalPrice(BigDecimal.valueOf(1200.90))
                .build();

        // When
        Page<OrderReportDTO> pageOfOrderReportDto = new PageImpl<>(Collections.singletonList(orderReportDTO));
        when(identity.getCustomUserDetails()).thenReturn(new CustomUserDetails(customer));
        when(orderRepository.findOrderStatisticsByCustomerId(userId, paginationRequest.toPageable())).thenReturn(pageOfOrderReportDto);

        // Then
        Page<OrderReportDTO> response = statisticsService.getOrderStatisticsByCustomerId(userId, paginationRequest);

        assertEquals(response, pageOfOrderReportDto);
        verify(orderRepository, Mockito.times(1)).findOrderStatisticsByCustomerId(Mockito.anyLong(), Mockito.any(Pageable.class));

    }

    @Test
    void givenCustomerIdAndPaginationRequest_whenCustomerRoleAndInvalidCustomerId_thenThrowAccessDeniedException() {

        // Given
        Long othersUserId = 10L;
        User customer = new UserBuilder().customer().build();

        PaginationRequest paginationRequest = PaginationRequest.builder()
                .page(1)
                .size(10)
                .build();

        // When
        when(identity.getCustomUserDetails()).thenReturn(new CustomUserDetails(customer));

        // Then
        assertThrows(
                AccessDeniedException.class,
                () -> statisticsService.getOrderStatisticsByCustomerId(othersUserId, paginationRequest)
        );
        verifyNoInteractions(orderRepository);

    }
    @Test
    void givenCustomerIdAndPaginationRequest_whenAdminRole_thenReturnPageOfOrderReportDto() {

        // Given
        Long userId = 5L;
        User customer = new UserBuilder().admin().build();

        PaginationRequest paginationRequest = PaginationRequest.builder()
                .page(1)
                .size(10)
                .build();

        OrderReportDTO orderReportDTO = OrderReportDTO.builder()
                .month(Month.SEPTEMBER.name())
                .year(2023)
                .totalOrderCount(100L)
                .totalBookCount(200L)
                .totalPrice(BigDecimal.valueOf(1200.90))
                .build();

        // When
        Page<OrderReportDTO> pageOfOrderReportDto = new PageImpl<>(Collections.singletonList(orderReportDTO));
        when(identity.getCustomUserDetails()).thenReturn(new CustomUserDetails(customer));
        when(orderRepository.findOrderStatisticsByCustomerId(userId, paginationRequest.toPageable())).thenReturn(pageOfOrderReportDto);

        // Then
        Page<OrderReportDTO> response = statisticsService.getOrderStatisticsByCustomerId(userId, paginationRequest);

        assertEquals(response, pageOfOrderReportDto);
        verify(orderRepository, Mockito.times(1)).findOrderStatisticsByCustomerId(Mockito.anyLong(), Mockito.any(Pageable.class));


    }

    @Test
    void givenPaginationRequest_whenSuccess_thenReturnOrderReportDTO() {

        // Given
        PaginationRequest paginationRequest = PaginationRequest.builder()
                .page(1)
                .size(10)
                .build();

        OrderReportDTO orderReportDTO = OrderReportDTO.builder()
                .month(Month.SEPTEMBER.name())
                .year(2023)
                .totalOrderCount(100L)
                .totalBookCount(200L)
                .totalPrice(BigDecimal.valueOf(1200.90))
                .build();

        // When
        Page<OrderReportDTO> pageOfOrderReportDto = new PageImpl<>(Collections.singletonList(orderReportDTO));
        when(orderRepository.findAllOrderStatistics(paginationRequest.toPageable())).thenReturn(pageOfOrderReportDto);

        // Then
        Page<OrderReportDTO> response = statisticsService.getAllOrderStatistics(paginationRequest);

        assertEquals(response, pageOfOrderReportDto);
        verify(orderRepository, Mockito.times(1)).findAllOrderStatistics(Mockito.any(Pageable.class));
    }
}