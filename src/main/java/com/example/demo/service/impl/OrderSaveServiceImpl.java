package com.example.demo.service.impl;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.exception.user.UserNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.model.mapper.order.OrderItemMapper;
import com.example.demo.model.mapper.order.OrderMapper;
import com.example.demo.payload.request.order.CreateOrderRequest;
import com.example.demo.repository.OrderRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderSaveService;
import com.example.demo.service.UserService;
import com.example.demo.util.Identity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link OrderSaveService} interface for creating and managing orders.
 */
@Service
@RequiredArgsConstructor
public class OrderSaveServiceImpl implements OrderSaveService {

    private final OrderItemService orderItemService;

    private final UserService userService;

    private final OrderRepository orderRepository;

    private final Identity identity;

    /**
     * Creates a new order based on the provided create order request.
     *
     * @param createOrderRequest The request containing order information to be used for creation.
     * @return An {@link OrderDTO} representing the newly created order.
     */
    @Transactional
    @Override
    public OrderDTO createOrder(CreateOrderRequest createOrderRequest) {

        CustomUserDetails customUserDetails = identity.getCustomUserDetails();

        User user = userService.findByEmail(customUserDetails.getEmail())
                .orElseThrow(() -> new UserNotFoundException(customUserDetails.getId()));

        List<OrderItemDTO> orderItemDTOs = createOrderRequest
                .getOrderDetailSet()
                .stream()
                .map(orderItemService::createOrderItem)
                .toList();

        Order order = Order.builder()
                .user(user)
                .build();

        order.setOrderItems(OrderItemMapper.toOrderItem(orderItemDTOs));

        return OrderMapper.toOrderDTO(orderRepository.save(order));

    }

}
