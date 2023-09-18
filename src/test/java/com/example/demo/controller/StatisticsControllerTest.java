package com.example.demo.controller;

import com.example.demo.base.BaseControllerTest;
import com.example.demo.builder.BookBuilder;
import com.example.demo.builder.UserBuilder;
import com.example.demo.dto.OrderReportDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.StatisticsService;
import com.example.demo.util.Identity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StatisticsControllerTest extends BaseControllerTest {

    @MockBean
    private StatisticsService statisticsService;

    @MockBean
    private Identity identity;

    @Test
    void givenCustomerIdAndPaginationRequest_WhenCustomerRole_ReturnOrderReportResponse() throws Exception {

        // given
        Long userId = 1L;
        Long orderId = 1L;

        User mockUser = new UserBuilder()
                .customer()
                .withId(userId)
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(mockUser);

        PaginationRequest paginationRequest = new PaginationRequest(0, 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 15);

        LocalDateTime createdDate = calendar.getTime().toInstant()
                .atZone(calendar.getTimeZone().toZoneId()).toLocalDateTime();

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

        Map<String, Integer> totalOrderCountByMonth = orderPage.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().getYear() + "-" + order.getCreatedAt().getMonth(),
                        Collectors.mapping(Order::getId, Collectors.collectingAndThen(Collectors.toSet(), Set::size))
                ));

        List<OrderReportDTO> reportDTOs = orderPage.get().map(order -> {
            String month = order.getCreatedAt().getMonth().toString();
            Integer year = order.getCreatedAt().getYear();

            String monthYearKey = year + "-" + month;
            Integer totalOrderCount = totalOrderCountByMonth.getOrDefault(monthYearKey, 0);

            Integer totalBookCount = order.getOrderItems().size();
            BigDecimal totalPrice = order.getOrderItems().stream()
                    .map(orderItem -> orderItem.getBook().getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new OrderReportDTO(month, year, totalOrderCount, totalBookCount, totalPrice);
        }).toList();

        Page<OrderReportDTO> expectedPage = new PageImpl<>(reportDTOs, orderPage.getPageable(), orderPage.getTotalElements());

        // when
        when(identity.getCustomUserDetails()).thenReturn(customUserDetails);
        when(statisticsService.getOrderStatisticsByCustomer(userId, paginationRequest)).thenReturn(expectedPage);

        // then
        mockMvc.perform(get("/api/v1/statistics/{customerId}", userId)
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paginationRequest)))
                .andExpect(status().isOk());

    }

    @Test
    void givenCustomerIdAndPaginationRequest_WhenAdminRole_ReturnOrderReportResponse() throws Exception {

        // given
        Long userId = 1L;
        Long orderId = 1L;

        User mockUser = new UserBuilder()
                .admin()
                .withId(userId)
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(mockUser);

        PaginationRequest paginationRequest = new PaginationRequest(0, 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 15);

        LocalDateTime createdDate = calendar.getTime().toInstant()
                .atZone(calendar.getTimeZone().toZoneId()).toLocalDateTime();

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

        Map<String, Integer> totalOrderCountByMonth = orderPage.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().getYear() + "-" + order.getCreatedAt().getMonth(),
                        Collectors.mapping(Order::getId, Collectors.collectingAndThen(Collectors.toSet(), Set::size))
                ));

        List<OrderReportDTO> reportDTOs = orderPage.get().map(order -> {
            String month = order.getCreatedAt().getMonth().toString();
            Integer year = order.getCreatedAt().getYear();

            String monthYearKey = year + "-" + month;
            Integer totalOrderCount = totalOrderCountByMonth.getOrDefault(monthYearKey, 0);

            Integer totalBookCount = order.getOrderItems().size();
            BigDecimal totalPrice = order.getOrderItems().stream()
                    .map(orderItem -> orderItem.getBook().getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new OrderReportDTO(month, year, totalOrderCount, totalBookCount, totalPrice);
        }).toList();

        Page<OrderReportDTO> expectedPage = new PageImpl<>(reportDTOs, orderPage.getPageable(), orderPage.getTotalElements());

        // when
        when(identity.getCustomUserDetails()).thenReturn(customUserDetails);
        when(statisticsService.getOrderStatisticsByCustomer(userId, paginationRequest)).thenReturn(expectedPage);

        // then
        mockMvc.perform(get("/api/v1/statistics/{customerId}", userId)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paginationRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void givenPaginationRequest_WhenAdminRole_ReturnOrderReportResponse() throws Exception {

        // given
        Long userId = 1L;
        Long orderId = 1L;

        User mockUser = new UserBuilder()
                .admin()
                .withId(userId)
                .build();


        PaginationRequest paginationRequest = new PaginationRequest(0, 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 15);

        LocalDateTime createdDate = calendar.getTime().toInstant()
                .atZone(calendar.getTimeZone().toZoneId()).toLocalDateTime();

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

        Map<String, Integer> totalOrderCountByMonth = orderPage.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().getYear() + "-" + order.getCreatedAt().getMonth(),
                        Collectors.mapping(Order::getId, Collectors.collectingAndThen(Collectors.toSet(), Set::size))
                ));

        List<OrderReportDTO> reportDTOs = orderPage.get().map(order -> {
            String month = order.getCreatedAt().getMonth().toString();
            Integer year = order.getCreatedAt().getYear();

            String monthYearKey = year + "-" + month;
            Integer totalOrderCount = totalOrderCountByMonth.getOrDefault(monthYearKey, 0);

            Integer totalBookCount = order.getOrderItems().size();
            BigDecimal totalPrice = order.getOrderItems().stream()
                    .map(orderItem -> orderItem.getBook().getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new OrderReportDTO(month, year, totalOrderCount, totalBookCount, totalPrice);
        }).toList();

        Page<OrderReportDTO> expectedPage = new PageImpl<>(reportDTOs, orderPage.getPageable(), orderPage.getTotalElements());

        // when
        when(statisticsService.getOrderStatistics(paginationRequest)).thenReturn(expectedPage);

        // then
        mockMvc.perform(get("/api/v1/statistics/allstatistics")
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paginationRequest)))
                .andExpect(status().isOk());

    }
}