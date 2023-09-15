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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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
                .orderItems(Set.of(orderItem1, orderItem2))
                .createdAt(createdDate)
                .build();

        Page<Order> orderPage = new PageImpl<>(Collections.singletonList(mockOrder));

        Integer year = orderPage.getContent().get(0).getCreatedAt().getYear();
        String month = orderPage.getContent().get(0).getCreatedAt().getMonth().toString();
        Integer totalOrderCount = orderPage.getSize();

        Integer totalBookCount = orderPage.stream()
                .map(order -> order.getOrderItems().size())
                .reduce(0, Integer::sum);

        BigDecimal totalPrice = orderPage.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .map(orderItem -> orderItem.getBook().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);;

        OrderReportDTO expected = OrderReportDTO.builder()
                .month(month)
                .year(year)
                .totalBookCount(totalBookCount)
                .totalPrice(totalPrice)
                .totalOrderCount(totalOrderCount)
                .build();

        // when
        when(orderRepository.findAllByUserId(anyLong(), any(Pageable.class))).thenReturn(orderPage);
        when(orderRepository.countDistinctOrdersByUserId(mockUser.getId())).thenReturn(totalOrderCount);

        // then
        OrderReportDTO result = statisticsService.getOrderStatisticsByCustomer(mockUser.getId(), paginationRequest);

        assertEquals(expected.getYear(), result.getYear());
        assertEquals(expected.getMonth(), result.getMonth());
        assertEquals(expected.getTotalBookCount(), result.getTotalBookCount());
        assertEquals(expected.getTotalOrderCount(), result.getTotalOrderCount());
        assertEquals(expected.getTotalPrice(), result.getTotalPrice());

        verify(orderRepository, times(1)).findAllByUserId(anyLong(), any(Pageable.class));
        verify(orderRepository, times(1)).countDistinctOrdersByUserId(anyLong());

    }

    @Test
    void givenPaginationRequest_When_ReturnOrderReportDTO() {

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
                .orderItems(Set.of(orderItem1, orderItem2))
                .createdAt(createdDate)
                .build();

        Page<Order> orderPage = new PageImpl<>(Collections.singletonList(mockOrder));

        Integer year = orderPage.getContent().get(0).getCreatedAt().getYear();
        String month = orderPage.getContent().get(0).getCreatedAt().getMonth().toString();
        Integer totalOrderCount = orderPage.getSize();

        Integer totalBookCount = orderPage.stream()
                .map(order -> order.getOrderItems().size())
                .reduce(0, Integer::sum);

        BigDecimal totalPrice = orderPage.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .map(orderItem -> orderItem.getBook().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);;

        OrderReportDTO expected = OrderReportDTO.builder()
                .month(month)
                .year(year)
                .totalBookCount(totalBookCount)
                .totalPrice(totalPrice)
                .totalOrderCount(totalOrderCount)
                .build();

        // when
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(orderPage);
        when(orderRepository.countDistinctOrders()).thenReturn(totalOrderCount);

        // then
        OrderReportDTO result = statisticsService.getOrderStatistics(paginationRequest);

        assertEquals(expected.getYear(), result.getYear());
        assertEquals(expected.getMonth(), result.getMonth());
        assertEquals(expected.getTotalBookCount(), result.getTotalBookCount());
        assertEquals(expected.getTotalOrderCount(), result.getTotalOrderCount());
        assertEquals(expected.getTotalPrice(), result.getTotalPrice());

        verify(orderRepository, times(1)).findAll(any(Pageable.class));
        verify(orderRepository, times(1)).countDistinctOrders();

    }
}