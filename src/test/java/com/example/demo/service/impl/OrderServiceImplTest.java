package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceTest;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.payload.request.pagination.DateIntervalRequest;
import com.example.demo.payload.request.pagination.PaginatedFindAllRequest;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import com.example.demo.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;


    @Test
    void givenOrderId_WhenOrderFound_ReturnOrderDTO() {

        // given
        Long orderId = 1L;

        User mockUser = User.builder()
                .email("mock@bookdelivery.com")
                .fullName("User FullName")
                .build();

        String mockBookId1 = RandomUtil.generateUUID();
        String mockBookId2 = RandomUtil.generateUUID();

        Book mockBook1 = Book.builder()
                .id(mockBookId1)
                .name("Name")
                .authorFullName("Author Full Name")
                .isbn("1234567890")
                .stock(321)
                .price(BigDecimal.TEN)
                .version(0L)
                .build();

        Book mockBook2 = Book.builder()
                .id(mockBookId2)
                .name("Name")
                .authorFullName("Author Full Name")
                .isbn("1234567890")
                .stock(321)
                .price(BigDecimal.TEN)
                .version(0L)
                .build();

        BookDTO bookDTO1 = BookDTO.builder()
                .id(mockBookId1)
                .name(mockBook1.getName())
                .authorFullName(mockBook1.getAuthorFullName())
                .price(mockBook1.getPrice())
                .isbn(mockBook1.getIsbn())
                .build();

        BookDTO bookDTO2 = BookDTO.builder()
                .id(mockBookId1)
                .name(mockBook1.getName())
                .authorFullName(mockBook1.getAuthorFullName())
                .price(mockBook1.getPrice())
                .isbn(mockBook1.getIsbn())
                .build();

        OrderItem orderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();


        Order mockOrder = Order.builder()
                .id(orderId)
                .user(mockUser)
                .orderItems(Set.of(orderItem1,orderItem2))
                .build();

        UserDTO userDTO = UserDTO.builder()
                .fullName(mockUser.getFullName())
                .build();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .book(bookDTO1)
                .build();

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .book(bookDTO2)
                .build();

        OrderDTO expected = OrderDTO.builder()
                .id(orderId)
                .user(userDTO)
                .orderItems(Set.of(orderItemDTO1,orderItemDTO2))
                .build();

        // when
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        // then
        OrderDTO actual = orderService.findOrderById(orderId);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUser().getId(), actual.getUser().getId());
        assertEquals(expected.getUser().getFullName(), actual.getUser().getFullName());
        assertEquals(expected.getOrderItems().size(), actual.getOrderItems().size());
        assertEquals(expected.getOrderItems().size(), actual.getOrderItems().size());

        // verify
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void givenCustomerIdAndPaginationRequest_WhenOrdersFound_ReturnPageOrderDTOList() {

        // Given
        Long customerId = 1L;
        Long orderId = 1L;
        PaginationRequest paginationRequest = new PaginationRequest(0, 10);
        Pageable pageRequest = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());

        User mockUser = User.builder()
                .email("mock@bookdelivery.com")
                .fullName("User FullName")
                .build();

        String mockBookId1 = RandomUtil.generateUUID();
        String mockBookId2 = RandomUtil.generateUUID();

        Book mockBook1 = Book.builder()
                .id(mockBookId1)
                .name("Name")
                .authorFullName("Author Full Name")
                .isbn("1234567890")
                .stock(321)
                .price(BigDecimal.TEN)
                .version(0L)
                .build();

        Book mockBook2 = Book.builder()
                .id(mockBookId2)
                .name("Name")
                .authorFullName("Author Full Name")
                .isbn("1234567890")
                .stock(321)
                .price(BigDecimal.TEN)
                .version(0L)
                .build();

        OrderItem orderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();

        Order mockOrder = Order.builder()
                .id(orderId)
                .user(mockUser)
                .orderItems(Set.of(orderItem1,orderItem2))
                .build();

        // When
        when(orderRepository.findAllByUserId(customerId, pageRequest))
                .thenReturn(new PageImpl<>(Collections.singletonList(mockOrder)));

        // Then
        Page<OrderDTO> result = orderService.findAllOrdersByCustomerId(customerId, paginationRequest);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        // verify
        verify(orderRepository, times(1)).findAllByUserId(customerId, pageRequest);
    }

    @Test
    void givenPaginatedFindAllRequest_WhenOrdersFound_ReturnPageOrderDTOList() {

        // Given
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar1.set(Calendar.DAY_OF_MONTH, 10);

        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar1.set(Calendar.DAY_OF_MONTH, 13);

        LocalDateTime startDate = calendar1.getTime().toInstant().atZone(calendar1.getTimeZone().toZoneId()).toLocalDateTime();
        LocalDateTime endDate = calendar2.getTime().toInstant().atZone(calendar2.getTimeZone().toZoneId()).toLocalDateTime();

        DateIntervalRequest dateIntervalRequest = new DateIntervalRequest(startDate, endDate);
        PaginationRequest paginationRequest = new PaginationRequest(0, 10);
        Pageable pageRequest = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());

        Long orderId = 1L;

        User mockUser = User.builder()
                .email("mock@bookdelivery.com")
                .fullName("User FullName")
                .build();

        String mockBookId1 = RandomUtil.generateUUID();
        String mockBookId2 = RandomUtil.generateUUID();

        Book mockBook1 = Book.builder()
                .id(mockBookId1)
                .name("Name")
                .authorFullName("Author Full Name")
                .isbn("1234567890")
                .stock(321)
                .price(BigDecimal.TEN)
                .version(0L)
                .build();

        Book mockBook2 = Book.builder()
                .id(mockBookId2)
                .name("Name")
                .authorFullName("Author Full Name")
                .isbn("1234567890")
                .stock(321)
                .price(BigDecimal.TEN)
                .version(0L)
                .build();

        OrderItem orderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();

        Order mockOrder = Order.builder()
                .id(orderId)
                .user(mockUser)
                .orderItems(Set.of(orderItem1,orderItem2))
                .build();

        // When
        when(orderRepository.findAllByCreatedAtBetween(dateIntervalRequest.getStartDate(),dateIntervalRequest.getEndDate(), pageRequest))
                .thenReturn(new PageImpl<>(Collections.singletonList(mockOrder)));

        // Then
        Page<OrderDTO> result = orderService.findAllOrdersBetweenTwoDatesAndPagination(
                new PaginatedFindAllRequest(dateIntervalRequest, paginationRequest));
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        // verify
        verify(orderRepository, times(1)).findAllByCreatedAtBetween(dateIntervalRequest.getStartDate(),dateIntervalRequest.getEndDate(), pageRequest);
    }
}