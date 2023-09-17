package com.example.demo.service.impl;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.mapper.order.OrderMapper;
import com.example.demo.payload.request.pagination.DateIntervalRequest;
import com.example.demo.payload.request.pagination.PaginatedFindAllRequest;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link OrderService} interface for managing orders.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id The unique identifier of the order.
     * @return An {@link OrderDTO} representing the order with the specified ID.
     */
    @Override
    public OrderDTO findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderMapper::toOrderDTO)
                .orElseThrow();
    }

    /**
     * Retrieves a paginated list of all orders associated with a customer based on their unique identifier.
     *
     * @param customerId The unique identifier of the customer.
     * @param paginationRequest The request containing pagination information.
     * @return A {@link Page} of {@link OrderDTO} objects representing the list of orders for the customer.
     */
    @Override
    public Page<OrderDTO> findAllOrdersByCustomerId(Long customerId, PaginationRequest paginationRequest) {

        return orderRepository.findAllByUserId(customerId, paginationRequest.toPageable())
                .map(OrderMapper::toOrderDTO);
    }

    /**
     * Retrieves a paginated list of all orders within a specified date interval.
     *
     * @param paginatedFindAllRequest The request containing date interval and pagination information.
     * @return A {@link Page} of {@link OrderDTO} objects representing the list of orders within the specified date interval.
     */
    @Override
    public Page<OrderDTO> findAllOrdersBetweenTwoDatesAndPagination(PaginatedFindAllRequest paginatedFindAllRequest) {

        DateIntervalRequest dateIntervalRequest = paginatedFindAllRequest.getDateIntervalRequest();
        PaginationRequest paginationRequest = paginatedFindAllRequest.getPaginationRequest();

        return orderRepository.findAllByCreatedAtBetween(
                        dateIntervalRequest.getStartDate(),
                        dateIntervalRequest.getEndDate(),
                        paginationRequest.toPageable())
                .map(OrderMapper::toOrderDTO);

    }

}
