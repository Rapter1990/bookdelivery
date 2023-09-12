package com.example.demo.logging.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOK_DELIVERY_LOGS")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private String endpoint;

    private String method;

    private String status;

    private String userInfo;

    private String errorType;

    private String response;

    private String operation;

    private LocalDateTime time;
}
