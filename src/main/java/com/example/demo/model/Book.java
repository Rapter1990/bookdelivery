package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

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

    @Version // Optimistic Locking
    private Long version;

}
