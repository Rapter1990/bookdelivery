package com.example.demo.dto;

import com.example.demo.model.Book;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemDTO {

    private Long id;
    private BookDTO book;
}
