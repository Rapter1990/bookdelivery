package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class BookDTO {

    private String id;
    private String isbn;
    private String name;
    private String authorFullName;
    private BigDecimal price;

}
