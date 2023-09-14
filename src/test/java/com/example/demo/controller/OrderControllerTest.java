package com.example.demo.controller;

import com.example.demo.base.BaseControllerTest;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.model.mapper.order.OrderMapper;
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

        User mockUser = User.builder()
                .id(userId)
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
                .id(mockBookId2)
                .name(mockBook2.getName())
                .authorFullName(mockBook2.getAuthorFullName())
                .price(mockBook2.getPrice())
                .isbn(mockBook2.getIsbn())
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(mockUser.getId())
                .fullName(mockUser.getFullName())
                .build();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .book(bookDTO1)
                .build();

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .book(bookDTO2)
                .build();

        OrderDTO mockOrderDTO = OrderDTO.builder()
                .id(orderId)
                .user(userDTO)
                .orderItems(new LinkedHashSet<>(Arrays.asList(orderItemDTO1, orderItemDTO2)))
                .build();

        OrderGetResponse expectedResponse = OrderMapper.toGetResponse(mockOrderDTO);
        CustomResponse<OrderGetResponse> expectedCustomResponse = CustomResponse.ok(expectedResponse);

        // when
        when(orderService.findOrderById(orderId)).thenReturn(mockOrderDTO);

        // then
        mockMvc.perform(get("/api/v1/orders/{orderId}", orderId)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedCustomResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(orderId))
                .andExpect(jsonPath("$.response.user.id").value(userId))
                .andExpect(jsonPath("$.response.orderItems[0].book.id").value(mockBookId1))
                .andExpect(jsonPath("$.response.orderItems[1].book.id").value(mockBookId2));
    }

    @Test
    void givenOrderId_WhenCustomerRoleAndOrderFound_ReturnOrderGetResponse() throws Exception {

        // given
        Long orderId = 1L;
        Long userId = 1L;

        User mockUser = User.builder()
                .id(userId)
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
                .id(mockBookId2)
                .name(mockBook2.getName())
                .authorFullName(mockBook2.getAuthorFullName())
                .price(mockBook2.getPrice())
                .isbn(mockBook2.getIsbn())
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(mockUser.getId())
                .fullName(mockUser.getFullName())
                .build();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .book(bookDTO1)
                .build();

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .book(bookDTO2)
                .build();

        OrderDTO mockOrderDTO = OrderDTO.builder()
                .id(orderId)
                .user(userDTO)
                .orderItems(new LinkedHashSet<>(Arrays.asList(orderItemDTO1, orderItemDTO2)))
                .build();

        OrderGetResponse expectedResponse = OrderMapper.toGetResponse(mockOrderDTO);
        CustomResponse<OrderGetResponse> expectedCustomResponse = CustomResponse.ok(expectedResponse);

        // when
        when(orderService.findOrderById(orderId)).thenReturn(mockOrderDTO);

        // then
        mockMvc.perform(get("/api/v1/orders/{orderId}", orderId)
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedCustomResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(orderId))
                .andExpect(jsonPath("$.response.user.id").value(userId))
                .andExpect(jsonPath("$.response.orderItems[0].book.id").value(mockBookId1))
                .andExpect(jsonPath("$.response.orderItems[1].book.id").value(mockBookId2));
    }

    @Test
    void givenCustomerIdAndPaginationRequest_WhenAdminRoleAndOrderFound_ReturnOrderGetByCustomerResponse() throws Exception {

        // given
        Long orderId = 1L;
        Long userId = 1L;

        User mockUser = User.builder()
                .id(userId)
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
                .id(mockBookId2)
                .name(mockBook2.getName())
                .authorFullName(mockBook2.getAuthorFullName())
                .price(mockBook2.getPrice())
                .isbn(mockBook2.getIsbn())
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(mockUser.getId())
                .fullName(mockUser.getFullName())
                .build();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .book(bookDTO1)
                .build();

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .book(bookDTO2)
                .build();

        OrderDTO mockOrderDTO = OrderDTO.builder()
                .id(orderId)
                .user(userDTO)
                .orderItems(new LinkedHashSet<>(Arrays.asList(orderItemDTO1, orderItemDTO2)))
                .build();

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
                        .content(objectMapper.writeValueAsString(expectedCustomResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(orderId))
                .andExpect(jsonPath("$.response.user.id").value(userId))
                .andExpect(jsonPath("$.response.orderItems[0].book.id").value(mockBookId1))
                .andExpect(jsonPath("$.response.orderItems[1].book.id").value(mockBookId2));

    }

    @Test
    void givenCustomerIdAndPaginationRequest_WhenCustomeRoleAndOrderFound_ReturnOrderGetByCustomerResponse() throws Exception {

        // given
        Long orderId = 1L;
        Long userId = 1L;

        User mockUser = User.builder()
                .id(userId)
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
                .id(mockBookId2)
                .name(mockBook2.getName())
                .authorFullName(mockBook2.getAuthorFullName())
                .price(mockBook2.getPrice())
                .isbn(mockBook2.getIsbn())
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(mockUser.getId())
                .fullName(mockUser.getFullName())
                .build();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .book(bookDTO1)
                .build();

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .book(bookDTO2)
                .build();

        OrderDTO mockOrderDTO = OrderDTO.builder()
                .id(orderId)
                .user(userDTO)
                .orderItems(new LinkedHashSet<>(Arrays.asList(orderItemDTO1, orderItemDTO2)))
                .build();

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
                        .content(objectMapper.writeValueAsString(expectedCustomResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(orderId))
                .andExpect(jsonPath("$.response.user.id").value(userId))
                .andExpect(jsonPath("$.response.orderItems[0].book.id").value(mockBookId1))
                .andExpect(jsonPath("$.response.orderItems[1].book.id").value(mockBookId2));

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

        DateIntervalRequest dateIntervalRequest = new DateIntervalRequest(startDate, endDate);
        PaginationRequest paginationRequest = new PaginationRequest(0, 10);
        PaginatedFindAllRequest paginatedFindAllRequest = PaginatedFindAllRequest.builder()
                .paginationRequest(paginationRequest)
                .dateIntervalRequest(dateIntervalRequest)
                .build();


        User mockUser = User.builder()
                .id(userId)
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
                .id(mockBookId2)
                .name(mockBook2.getName())
                .authorFullName(mockBook2.getAuthorFullName())
                .price(mockBook2.getPrice())
                .isbn(mockBook2.getIsbn())
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(mockUser.getId())
                .fullName(mockUser.getFullName())
                .build();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .book(bookDTO1)
                .build();

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .book(bookDTO2)
                .build();

        OrderDTO mockOrderDTO = OrderDTO.builder()
                .id(orderId)
                .user(userDTO)
                .orderItems(new LinkedHashSet<>(Arrays.asList(orderItemDTO1, orderItemDTO2)))
                .build();

        Page<OrderDTO> mockPageOfOrderDTOs = new PageImpl<>(Collections.singletonList(mockOrderDTO));

        CustomPageResponse<OrderGetBetweenDatesResponse> expectedResponse = OrderMapper.toGetBetweenDatesResponses(mockPageOfOrderDTOs);
        CustomResponse<CustomPageResponse<OrderGetBetweenDatesResponse>> expectedCustomResponse = CustomResponse.ok(expectedResponse);

        // when
        when(orderService.findAllOrdersBetweenTwoDatesAndPagination(paginatedFindAllRequest)).thenReturn(mockPageOfOrderDTOs);

        // then
        mockMvc.perform(get("/between-dates")
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedCustomResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(orderId))
                .andExpect(jsonPath("$.response.user.id").value(userId))
                .andExpect(jsonPath("$.response.orderItems[0].book.id").value(mockBookId1))
                .andExpect(jsonPath("$.response.orderItems[1].book.id").value(mockBookId2));
    }

}