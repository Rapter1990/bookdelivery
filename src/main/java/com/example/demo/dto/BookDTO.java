package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class BookDTO {

    private String id;
    private String isbn;
    private String name;
    private String authorFullName;
    private BigDecimal price;

}
