package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;

/**
 * Repository interface for accessing and managing Order entities in the database.
 * This interface extends JpaRepository to provide basic CRUD operations for Order entities.
 * It also extends JpaSpecificationExecutor to support dynamic query creation using specifications.
 */
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    /**
     * Retrieves a page of orders associated with a specific user.
     *
     * @param userId         The unique identifier of the user.
     * @param orderPageable  Pageable object specifying the page and sorting options.
     * @return A Page containing the orders associated with the user.
     */
    Page<Order> findAllByUserId(Long userId, Pageable orderPageable);

    /**
     * Retrieves a page of orders created within a specified time range.
     *
     * @param startDate      The start date of the time range.
     * @param endTime        The end date of the time range.
     * @param orderPageable  Pageable object specifying the page and sorting options.
     * @return A Page containing the orders created within the specified time range.
     */
    Page<Order> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endTime, Pageable orderPageable);

}
