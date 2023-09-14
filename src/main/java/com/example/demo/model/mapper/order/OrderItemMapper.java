package com.example.demo.model.mapper.order;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.model.Book;
import com.example.demo.model.OrderItem;
import lombok.experimental.UtilityClass;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@UtilityClass
public class OrderItemMapper {


    public OrderItemDTO toDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .book(toOrderItemBook(orderItem.getBook()))
                .build();
    }

    public Set<OrderItemDTO> toDTO(Set<OrderItem> orderItems) {

        List<OrderItemDTO> orderItemDtos = orderItems.stream()
                .map(OrderItemMapper::toDTO)
                .toList();

        return new LinkedHashSet<>(orderItemDtos);
    }

    private OrderItemDTO.OrderItemBook toOrderItemBook(Book source) {
        return OrderItemDTO.OrderItemBook.builder()
                .id(source.getId())
                .name(source.getName())
                .authorFullName(source.getAuthorFullName())
                .isbn(source.getIsbn())
                .price(source.getPrice())
                .build();
    }
}
