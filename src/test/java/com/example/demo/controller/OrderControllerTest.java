package com.example.demo.controller;

import com.example.demo.base.BaseControllerTest;
import com.example.demo.builder.BookBuilder;
import com.example.demo.builder.UserBuilder;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.model.mapper.book.BookMapper;
import com.example.demo.model.mapper.order.OrderMapper;
import com.example.demo.model.mapper.user.UserMapper;
import com.example.demo.payload.request.pagination.DateIntervalRequest;
import com.example.demo.payload.request.pagination.PaginatedFindAllRequest;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.payload.response.CustomPageResponse;
import com.example.demo.payload.response.CustomResponse;
import com.example.demo.payload.response.order.OrderGetBetweenDatesResponse;
import com.example.demo.payload.response.order.OrderGetByCustomerResponse;
import com.example.demo.payload.response.order.OrderGetResponse;
import com.example.demo.service.OrderSaveService;
import com.example.demo.service.OrderService;
import com.example.demo.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest extends BaseControllerTest {

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderSaveService orderSaveService;

    @Test
    void createOrder() {
    }

    @Test
    void givenOrderId_WhenAdminRoleAndOrderFound_ReturnOrderGetResponse() throws Exception {

        // given
        Long orderId = 1L;
        Long userId = 1L;

        User mockUser = new UserBuilder()
                .customer()
                .withId(userId)
                .build();

        Book mockBook1 = new BookBuilder().withValidFields().build();
        Book mockBook2 = new BookBuilder().withValidFields().build();

        OrderItem mockOrderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();
        OrderItem mockOrderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();

        Order mockOrder = Order.builder()
                .id(orderId)
                .orderItems(new LinkedHashSet<>(Arrays.asList(mockOrderItem1,mockOrderItem2)))
                .user(mockUser)
                .build();

        OrderDTO mockOrderDTO = OrderMapper.toOrderDTO(mockOrder);

        OrderGetResponse expectedResponse = OrderMapper.toGetResponse(mockOrderDTO);
        CustomResponse<OrderGetResponse> expectedCustomResponse = CustomResponse.ok(expectedResponse);

        // when
        when(orderService.findOrderById(orderId)).thenReturn(mockOrderDTO);

        // then
        mockMvc.perform(get("/api/v1/orders/{orderId}", orderId)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(orderId))
                .andExpect(jsonPath("$.response.user.id").value(userId))
                .andExpect(jsonPath("$.response.orderItems[0].book.id").value(mockBook1.getId()))
                .andExpect(jsonPath("$.response.orderItems[1].book.id").value(mockBook2.getId()))
                .andExpect(jsonPath("$.isSuccess").value(expectedCustomResponse.getIsSuccess()))
                .andExpect(jsonPath("$.httpStatus").value(expectedCustomResponse.getHttpStatus().getReasonPhrase()))
                .andExpect(jsonPath("$.time").isNotEmpty());
    }

    @Test
    void givenOrderId_WhenCustomerRoleAndOrderFound_ReturnOrderGetResponse() throws Exception {

// given
        Long orderId = 1L;
        Long userId = 1L;

        User mockUser = new UserBuilder()
                .customer()
                .withId(userId)
                .build();

        Book mockBook1 = new BookBuilder().withValidFields().build();
        Book mockBook2 = new BookBuilder().withValidFields().build();

        OrderItem mockOrderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();
        OrderItem mockOrderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();

        Order mockOrder = Order.builder()
                .id(1L)
                .orderItems(new LinkedHashSet<>(Arrays.asList(mockOrderItem1,mockOrderItem2)))
                .user(mockUser)
                .build();

        OrderDTO mockOrderDTO = OrderMapper.toOrderDTO(mockOrder);

        OrderGetResponse expectedResponse = OrderMapper.toGetResponse(mockOrderDTO);
        CustomResponse<OrderGetResponse> expectedCustomResponse = CustomResponse.ok(expectedResponse);

        // when
        when(orderService.findOrderById(orderId)).thenReturn(mockOrderDTO);

        // then
        mockMvc.perform(get("/api/v1/orders/{orderId}", orderId)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(orderId))
                .andExpect(jsonPath("$.response.user.id").value(userId))
                .andExpect(jsonPath("$.response.orderItems[0].book.id").value(mockBook1.getId()))
                .andExpect(jsonPath("$.response.orderItems[1].book.id").value(mockBook2.getId()))
                .andExpect(jsonPath("$.isSuccess").value(expectedCustomResponse.getIsSuccess()))
                .andExpect(jsonPath("$.httpStatus").value(expectedCustomResponse.getHttpStatus().getReasonPhrase()))
                .andExpect(jsonPath("$.time").isNotEmpty());
    }

    @Test
    void givenCustomerIdAndPaginationRequest_WhenAdminRoleAndOrderFound_ReturnOrderGetByCustomerResponse() throws Exception {

        // given
        Long orderId = 1L;
        Long userId = 1L;

        User mockUser = new UserBuilder()
                .customer()
                .withId(userId)
                .build();

        Book mockBook1 = new BookBuilder().withValidFields().build();
        Book mockBook2 = new BookBuilder().withValidFields().build();

        OrderItem mockOrderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();
        OrderItem mockOrderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();

        Order mockOrder = Order.builder()
                .id(orderId)
                .orderItems(new LinkedHashSet<>(Arrays.asList(mockOrderItem1,mockOrderItem2)))
                .user(mockUser)
                .build();

        OrderDTO mockOrderDTO = OrderMapper.toOrderDTO(mockOrder);

        Page<OrderDTO> mockPageOfOrderDTOs = new PageImpl<>(Collections.singletonList(mockOrderDTO));

        CustomPageResponse<OrderGetByCustomerResponse> expectedResponse = OrderMapper.toGetByCustomerResponse(mockPageOfOrderDTOs);
        CustomResponse<CustomPageResponse<OrderGetByCustomerResponse>> expectedCustomResponse = CustomResponse.ok(expectedResponse);

        PaginationRequest paginationRequest = new PaginationRequest(0, 10);

        // when
        when(orderService.findAllOrdersByCustomerId(userId, paginationRequest)).thenReturn(mockPageOfOrderDTOs);

        // then
        mockMvc.perform(get("/api/v1/orders/customer/{customerId}", userId)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paginationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.content[0].id").value(orderId))
                .andExpect(jsonPath("$.response.content[0].user.id").value(userId))
                .andExpect(jsonPath("$.response.content[0].orderItems[0].book.id").value(mockBook1.getId()))
                .andExpect(jsonPath("$.response.content[0].orderItems[1].book.id").value(mockBook2.getId()))
                .andExpect(jsonPath("$.isSuccess").value(expectedCustomResponse.getIsSuccess()))
                .andExpect(jsonPath("$.httpStatus").value(expectedCustomResponse.getHttpStatus().getReasonPhrase()))
                .andExpect(jsonPath("$.time").isNotEmpty());

    }

    @Test
    void givenCustomerIdAndPaginationRequest_WhenCustomerRoleAndOrderFound_ReturnOrderGetByCustomerResponse() throws Exception {

        // given
        Long orderId = 1L;
        Long userId = 1L;

        User mockUser = new UserBuilder()
                .customer()
                .withId(userId)
                .build();

        Book mockBook1 = new BookBuilder().withValidFields().build();
        Book mockBook2 = new BookBuilder().withValidFields().build();

        OrderItem mockOrderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();
        OrderItem mockOrderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();

        Order mockOrder = Order.builder()
                .id(orderId)
                .orderItems(new LinkedHashSet<>(Arrays.asList(mockOrderItem1,mockOrderItem2)))
                .user(mockUser)
                .build();

        OrderDTO mockOrderDTO = OrderMapper.toOrderDTO(mockOrder);

        Page<OrderDTO> mockPageOfOrderDTOs = new PageImpl<>(Collections.singletonList(mockOrderDTO));

        CustomPageResponse<OrderGetByCustomerResponse> expectedResponse = OrderMapper.toGetByCustomerResponse(mockPageOfOrderDTOs);
        CustomResponse<CustomPageResponse<OrderGetByCustomerResponse>> expectedCustomResponse = CustomResponse.ok(expectedResponse);

        PaginationRequest paginationRequest = new PaginationRequest(0, 10);

        // when
        when(orderService.findAllOrdersByCustomerId(userId, paginationRequest)).thenReturn(mockPageOfOrderDTOs);

        // then
        mockMvc.perform(get("/api/v1/orders/customer/{customerId}", userId)
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paginationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.content[0].id").value(orderId))
                .andExpect(jsonPath("$.response.content[0].user.id").value(userId))
                .andExpect(jsonPath("$.response.content[0].orderItems[0].book.id").value(mockBook1.getId()))
                .andExpect(jsonPath("$.response.content[0].orderItems[1].book.id").value(mockBook2.getId()))
                .andExpect(jsonPath("$.isSuccess").value(expectedCustomResponse.getIsSuccess()))
                .andExpect(jsonPath("$.httpStatus").value(expectedCustomResponse.getHttpStatus().getReasonPhrase()))
                .andExpect(jsonPath("$.time").isNotEmpty());

    }

    @Test
    void givenPaginatedFindAllRequest_WhenAdminRole_ReturnOrdersBetweenTwoDates() throws Exception {

        // given
        Long orderId = 1L;
        Long userId = 1L;

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

        PaginatedFindAllRequest request = PaginatedFindAllRequest.builder()
                .dateIntervalRequest(new DateIntervalRequest(startDate, endDate))
                .paginationRequest(new PaginationRequest(1, 10))
                .build();

        User mockUser = new UserBuilder()
                .customer()
                .withId(userId)
                .build();

        Book mockBook1 = new BookBuilder().withValidFields().build();
        Book mockBook2 = new BookBuilder().withValidFields().build();

        OrderItem mockOrderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();
        OrderItem mockOrderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();

        Order mockOrder = Order.builder()
                .id(orderId)
                .orderItems(new LinkedHashSet<>(Arrays.asList(mockOrderItem1,mockOrderItem2)))
                .user(mockUser)
                .build();

        OrderDTO mockOrderDTO = OrderMapper.toOrderDTO(mockOrder);

        Page<OrderDTO> mockPageOfOrderDTOs = new PageImpl<>(Collections.singletonList(mockOrderDTO));

        CustomPageResponse<OrderGetBetweenDatesResponse> expectedResponse = OrderMapper.toGetBetweenDatesResponses(mockPageOfOrderDTOs);
        CustomResponse<CustomPageResponse<OrderGetBetweenDatesResponse>> expectedCustomResponse = CustomResponse.ok(expectedResponse);

        // when
        when(orderService.findAllOrdersBetweenTwoDatesAndPagination(any(PaginatedFindAllRequest.class))).thenReturn(mockPageOfOrderDTOs);

        // then
        mockMvc.perform(post("/api/v1/orders/between-dates")
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.content[0].id").value(orderId))
                .andExpect(jsonPath("$.response.content[0].user.id").value(userId))
                .andExpect(jsonPath("$.response.content[0].orderItems[0].book.id").value(mockBook1.getId()))
                .andExpect(jsonPath("$.response.content[0].orderItems[1].book.id").value(mockBook2.getId()))
                .andExpect(jsonPath("$.isSuccess").value(expectedCustomResponse.getIsSuccess()))
                .andExpect(jsonPath("$.httpStatus").value(expectedCustomResponse.getHttpStatus().getReasonPhrase()))
                .andExpect(jsonPath("$.time").isNotEmpty());
    }

}