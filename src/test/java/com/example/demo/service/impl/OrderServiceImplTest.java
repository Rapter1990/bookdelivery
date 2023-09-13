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
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import com.example.demo.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void createOrder() {

    }

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


        Order order = Order.builder()
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
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // then
        OrderDTO actual = orderService.findOrderById(orderId);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUser().getId(), actual.getUser().getId());
        assertEquals(expected.getUser().getFullName(), actual.getUser().getFullName());
        assertEquals(expected.getOrderItems().size(), actual.getOrderItems().size());
        assertEquals(expected.getOrderItems().size(), actual.getOrderItems().size());

        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void findAllOrdersByCustomerId() {

    }

    @Test
    void findAllOrdersBetweenTwoDatesAndPagination() {

    }
}