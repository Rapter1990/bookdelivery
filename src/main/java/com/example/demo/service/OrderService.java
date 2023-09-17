package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.payload.request.pagination.PaginatedFindAllRequest;
import com.example.demo.payload.request.pagination.PaginationRequest;
import org.springframework.data.domain.Page;

/**
 * This interface defines a service for managing orders.
 */
public interface OrderService {

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id The unique identifier of the order.
     * @return An {@link OrderDTO} representing the order with the specified ID.
     */
    OrderDTO findOrderById(Long id);

    /**
     * Retrieves a paginated list of all orders associated with a customer based on their unique identifier.
     *
     * @param customerId The unique identifier of the customer.
     * @param paginationRequest The request containing pagination information.
     * @return A {@link Page} of {@link OrderDTO} objects representing the list of orders for the customer.
     */
    Page<OrderDTO> findAllOrdersByCustomerId(Long customerId, PaginationRequest paginationRequest);

    /**
     * Retrieves a paginated list of all orders within a specified date interval.
     *
     * @param paginatedFindAllRequest The request containing date interval and pagination information.
     * @return A {@link Page} of {@link OrderDTO} objects representing the list of orders within the specified date interval.
     */
    Page<OrderDTO> findAllOrdersBetweenTwoDatesAndPagination(PaginatedFindAllRequest paginatedFindAllRequest);

}
