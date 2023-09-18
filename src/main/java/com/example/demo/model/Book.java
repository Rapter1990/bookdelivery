package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKS")
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTHOER_FULL_NAME")
    private String authorFullName;

    @Column(name = "STOCK")
    private Integer stock;

    @Column(name = "PRICE")
    private BigDecimal price;

    @OneToMany(mappedBy = "book")
    private List<OrderItem> orderItems;

}
