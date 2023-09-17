package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceTest;
import com.example.demo.builder.BookBuilder;
import com.example.demo.builder.UserBuilder;
import com.example.demo.dto.OrderReportDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StatisticsServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void givenCustomerIdAndPaginationRequest_WhenCustomerIdFound_ReturnOrderReportDTO() {

        // given
        Long orderId = 1L;

        PaginationRequest paginationRequest = PaginationRequest.builder()
                .page(1)
                .size(10)
                .build();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 15);

        LocalDateTime createdDate = calendar.getTime().toInstant()
                .atZone(calendar.getTimeZone().toZoneId()).toLocalDateTime();

        User mockUser = new UserBuilder().customer().build();

        Book mockBook1 = new BookBuilder().withValidFields().build();

        Book mockBook2 = new BookBuilder().withValidFields().build();

        OrderItem orderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();

        Order mockOrder = Order.builder()
                .id(orderId)
                .user(mockUser)
                .orderItems(List.of(orderItem1, orderItem2))
                .createdAt(createdDate)
                .build();

        Page<Order> orderPage = new PageImpl<>(Collections.singletonList(mockOrder));

        Map<String, List<Order>> ordersByMonthYear = orderPage.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().getYear() + "-" + order.getCreatedAt().getMonth()
                ));

        List<OrderReportDTO> reportDTOs = ordersByMonthYear.entrySet().stream()
                .map(entry -> {
                    String monthYearKey = entry.getKey();
                    List<Order> ordersForMonthYear = entry.getValue();
                    int totalOrderCount = ordersForMonthYear.size();

                    int totalBookCount = ordersForMonthYear.stream()
                            .mapToInt(order -> order.getOrderItems().size())
                            .sum();

                    BigDecimal totalPrice = ordersForMonthYear.stream()
                            .flatMap(order -> order.getOrderItems().stream())
                            .map(orderItem -> orderItem.getBook().getPrice())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    // Splitting the monthYearKey to extract month and year
                    String[] parts = monthYearKey.split("-");
                    String month = parts[1];
                    int year = Integer.parseInt(parts[0]);

                    return new OrderReportDTO(month, year, totalOrderCount, totalBookCount, totalPrice);
                })
                .toList();

        Page<OrderReportDTO> expectedPage = new PageImpl<>(reportDTOs, orderPage.getPageable(), orderPage.getTotalElements());

        // when
        when(orderRepository.findAllByUserId(anyLong(), any(Pageable.class))).thenReturn(orderPage);

        // then
        Page<OrderReportDTO> result = statisticsService.getOrderStatisticsByCustomer(mockUser.getId(), paginationRequest);

        assertEquals(expectedPage.getTotalElements(), result.getTotalElements());
        assertEquals(expectedPage.getTotalPages(), result.getTotalPages());
        assertEquals(expectedPage.getSize(), result.getSize());
        assertEquals(expectedPage.getNumber(), result.getNumber());

        List<OrderReportDTO> expectedContent = expectedPage.getContent();
        List<OrderReportDTO> resultContent = result.getContent();
        assertEquals(expectedContent.size(), resultContent.size());
        for (int i = 0; i < expectedContent.size(); i++) {
            OrderReportDTO expectedDTO = expectedContent.get(i);
            OrderReportDTO resultDTO = resultContent.get(i);
            assertEquals(expectedDTO.getYear(), resultDTO.getYear());
            assertEquals(expectedDTO.getMonth(), resultDTO.getMonth());
            assertEquals(expectedDTO.getTotalBookCount(), resultDTO.getTotalBookCount());
            assertEquals(expectedDTO.getTotalOrderCount(), resultDTO.getTotalOrderCount());
            assertEquals(expectedDTO.getTotalPrice(), resultDTO.getTotalPrice());
        }

        verify(orderRepository, times(1)).findAllByUserId(anyLong(), any(Pageable.class));

    }

    @Test
    void givenPaginationRequest_WhenAdminRole_ReturnOrderReportDTO() {

        // given
        Long orderId = 1L;

        PaginationRequest paginationRequest = PaginationRequest.builder()
                .page(1)
                .size(10)
                .build();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 15);

        LocalDateTime createdDate = calendar.getTime().toInstant()
                .atZone(calendar.getTimeZone().toZoneId()).toLocalDateTime();

        User mockUser = new UserBuilder().customer().build();

        Book mockBook1 = new BookBuilder().withValidFields().build();

        Book mockBook2 = new BookBuilder().withValidFields().build();

        OrderItem orderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();

        Order mockOrder = Order.builder()
                .id(orderId)
                .user(mockUser)
                .orderItems(List.of(orderItem1, orderItem2))
                .createdAt(createdDate)
                .build();

        Page<Order> orderPage = new PageImpl<>(Collections.singletonList(mockOrder));

        Map<String, List<Order>> ordersByMonthYear = orderPage.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().getYear() + "-" + order.getCreatedAt().getMonth()
                ));

        List<OrderReportDTO> reportDTOs = ordersByMonthYear.entrySet().stream()
                .map(entry -> {
                    String monthYearKey = entry.getKey();
                    List<Order> ordersForMonthYear = entry.getValue();
                    int totalOrderCount = ordersForMonthYear.size();

                    int totalBookCount = ordersForMonthYear.stream()
                            .mapToInt(order -> order.getOrderItems().size())
                            .sum();

                    BigDecimal totalPrice = ordersForMonthYear.stream()
                            .flatMap(order -> order.getOrderItems().stream())
                            .map(orderItem -> orderItem.getBook().getPrice())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    // Splitting the monthYearKey to extract month and year
                    String[] parts = monthYearKey.split("-");
                    String month = parts[1];
                    int year = Integer.parseInt(parts[0]);

                    return new OrderReportDTO(month, year, totalOrderCount, totalBookCount, totalPrice);
                })
                .toList();

        Page<OrderReportDTO> expectedPage = new PageImpl<>(reportDTOs, orderPage.getPageable(), orderPage.getTotalElements());

        // when
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(orderPage);

        // then
        Page<OrderReportDTO> result = statisticsService.getOrderStatistics(paginationRequest);

        assertEquals(expectedPage.getTotalElements(), result.getTotalElements());
        assertEquals(expectedPage.getTotalPages(), result.getTotalPages());
        assertEquals(expectedPage.getSize(), result.getSize());
        assertEquals(expectedPage.getNumber(), result.getNumber());

        List<OrderReportDTO> expectedContent = expectedPage.getContent();
        List<OrderReportDTO> resultContent = result.getContent();
        assertEquals(expectedContent.size(), resultContent.size());
        for (int i = 0; i < expectedContent.size(); i++) {
            OrderReportDTO expectedDTO = expectedContent.get(i);
            OrderReportDTO resultDTO = resultContent.get(i);
            assertEquals(expectedDTO.getYear(), resultDTO.getYear());
            assertEquals(expectedDTO.getMonth(), resultDTO.getMonth());
            assertEquals(expectedDTO.getTotalBookCount(), resultDTO.getTotalBookCount());
            assertEquals(expectedDTO.getTotalOrderCount(), resultDTO.getTotalOrderCount());
            assertEquals(expectedDTO.getTotalPrice(), resultDTO.getTotalPrice());
        }

        verify(orderRepository, times(1)).findAll(any(Pageable.class));

    }
}