package com.example.demo.model.mapper.order;


import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.model.mapper.user.UserMapper;
import com.example.demo.payload.response.order.OrderGetResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderMapper {

    public static OrderDTO toOrderDTO(Order source) {
        return OrderDTO.builder()
                .id(source.getId())
                .user(UserMapper.toDTO(source.getUser()))
                .orderItems(OrderItemMapper.toDTO(source.getOrderItems()))
                .createdAt(source.getCreatedAt())
                .build();
    }

    public static OrderGetResponse toGetResponse(OrderDTO source) {
        return OrderGetResponse.builder()
                .id(source.getId())
                .user(source.getUser())
                .orderItems(source.getOrderItems())
                .createdAt(source.getCreatedAt())
                .build();
    }

}
