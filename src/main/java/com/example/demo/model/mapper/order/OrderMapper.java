package com.example.demo.model.mapper.order;


import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.model.mapper.user.UserMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderMapper {


    public static OrderDTO toOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .user(UserMapper.toDTO(order.getUser()))
                .orderItems(OrderItemMapper.toDTO(order.getOrderItems()))
                .createdAt(order.getCreatedAt())
                .build();
    }


}
