package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceTest;
import com.example.demo.builder.BookBuilder;
import com.example.demo.builder.UserBuilder;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.model.mapper.order.OrderItemMapper;
import com.example.demo.model.mapper.order.OrderMapper;
import com.example.demo.payload.request.order.CreateOrderRequest;
import com.example.demo.payload.request.order.OrderItemRequest;
import com.example.demo.repository.OrderRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.UserService;
import com.example.demo.util.Identity;
import com.example.demo.util.RandomUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

class OrderSaveServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private OrderSaveServiceImpl orderSaveService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private UserService userService;

    @Mock
    private Identity identity;

    @Test
    void givenValidCreateOrderRequest_whenOrderCreated_thenReturnOrderDTO() {

        // Given
        String bookId1 = RandomUtil.generateUUID();
        String bookId2 = RandomUtil.generateUUID();

        User user = new UserBuilder().customer().build();
        CustomUserDetails userDetails = new CustomUserDetails(user);

        OrderItemRequest mockOrderItemRequest1 = OrderItemRequest.builder()
                .bookId(bookId1)
                .amount(RandomUtil.generateRandomInteger(0, 5))
                .build();

        OrderItem orderItem1 = OrderItem.builder()
                .id(1L)
                .book(new BookBuilder().withValidFields().build())
                .build();
        OrderItemDTO orderItemDTO1 = OrderItemMapper.toDTO(orderItem1);

        OrderItemRequest mockOrderItemRequest2 = OrderItemRequest.builder()
                .bookId(bookId2)
                .amount(RandomUtil.generateRandomInteger(0, 5))
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .id(2L)
                .book(new BookBuilder().withValidFields().build())
                .build();
        OrderItemDTO orderItemDTO2 = OrderItemMapper.toDTO(orderItem2);

        CreateOrderRequest mockCreateOrderRequest = CreateOrderRequest.builder()
                .orderDetailSet(new LinkedHashSet<>(List.of(mockOrderItemRequest1, mockOrderItemRequest2)))
                .build();

        Order order = Order.builder()
                .id(1L)
                .user(user)
                .orderItems(List.of(orderItem1, orderItem2))
                .build();

        OrderDTO expected = OrderMapper.toOrderDTO(order);

        // When
        Mockito.when(identity.getCustomUserDetails()).thenReturn(userDetails);
        Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(orderItemService.createOrderItem(mockOrderItemRequest1)).thenReturn(orderItemDTO1);
        Mockito.when(orderItemService.createOrderItem(mockOrderItemRequest2)).thenReturn(orderItemDTO2);
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        // Then
        OrderDTO response = orderSaveService.createOrder(mockCreateOrderRequest);

        Assertions.assertEquals(expected, response);
        Mockito.verify(identity, Mockito.times(1)).getCustomUserDetails();
        Mockito.verify(orderItemService, Mockito.times(2)).createOrderItem(Mockito.any(OrderItemRequest.class));
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Order.class));
    }

}