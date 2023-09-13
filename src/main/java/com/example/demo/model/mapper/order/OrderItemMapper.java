package com.example.demo.model.mapper.order;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.model.OrderItem;
import com.example.demo.model.mapper.book.BookMapper;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class OrderItemMapper {


    public OrderItemDTO toDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .book(BookMapper.toDTO(orderItem.getBook()))
                .build();
    }

    public Set<OrderItemDTO> toDTO(Set<OrderItem> orderItems) {

        List<OrderItemDTO> orderItemDtos = orderItems.stream()
                .map(OrderItemMapper::toDTO).toList();

        return new HashSet<OrderItemDTO>(orderItemDtos);
    }

}
