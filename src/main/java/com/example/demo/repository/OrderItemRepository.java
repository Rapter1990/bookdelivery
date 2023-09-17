package com.example.demo.repository;

import com.example.demo.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing and managing OrderItem entities in the database.
 * This interface extends JpaRepository to provide basic CRUD operations for OrderItem entities.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
