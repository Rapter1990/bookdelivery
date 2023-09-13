package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Page<Order> findAllByUserId(Long userId, Pageable orderPageable);

    Page<Order> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endTime, Pageable orderPageable);
}
