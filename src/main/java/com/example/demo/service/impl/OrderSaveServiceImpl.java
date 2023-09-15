package com.example.demo.service.impl;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.book.UserNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.model.mapper.order.OrderMapper;
import com.example.demo.model.mapper.user.UserMapper;
import com.example.demo.payload.request.order.CreateOrderRequest;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderSaveService;
import com.example.demo.service.UserService;
import com.example.demo.util.Identity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderSaveServiceImpl implements OrderSaveService {

    private final OrderItemService orderItemService;

    private final UserService userService;

    private final OrderRepository orderRepository;

    private final Identity identity;

    @Transactional
    @Override
    public OrderDTO createOrder(CreateOrderRequest createOrderRequest) {

        User user = userService.findByEmail(identity.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(identity.getId())));

        UserDTO userDTO = UserMapper.toDTO(user);

        Set<OrderItemDTO> orderDetailDTOSet = createOrderRequest
                .getOrderDetailSet()
                .stream()
                .map(orderItemService::createOrderItem)
                .collect(Collectors.toSet());

        OrderDTO orderDTO = OrderDTO.builder()
                .user(userDTO)
                .orderItems(orderDetailDTOSet)
                .createdAt(LocalDateTime.now())
                .build();

        Order order = OrderMapper.toOrder(orderDTO);

        Order orderCompleted = orderRepository.save(order);

        return OrderMapper.toOrderDTO(orderCompleted);
    }
}
