package com.example.demo.service.impl;

import com.example.demo.dto.OrderDTO;
import com.example.demo.exception.order.OrderNotFoundException;
import com.example.demo.model.enums.Role;
import com.example.demo.model.mapper.order.OrderMapper;
import com.example.demo.payload.request.pagination.DateIntervalRequest;
import com.example.demo.payload.request.pagination.PaginatedFindAllRequest;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.repository.OrderRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.OrderService;
import com.example.demo.util.Identity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link OrderService} interface for managing orders.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final Identity identity;

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id The unique identifier of the order.
     * @return An {@link OrderDTO} representing the order with the specified ID.
     */
    @Override
    public OrderDTO findOrderById(Long id) {

        CustomUserDetails userDetails = identity.getCustomUserDetails();

        return orderRepository.findById(id)
                .map(order -> {
                    // Check access based on customUserDetails here
                    if ((userDetails.getId().equals(order.getUser().getId()) &&
                            userDetails.getUser().getRole().equals(Role.ROLE_CUSTOMER))
                            || userDetails.getUser().getRole().equals(Role.ROLE_ADMIN)) {
                        return OrderMapper.toOrderDTO(order);
                    } else {
                        throw new AccessDeniedException("You cannot access this order by Id");
                    }
                })
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
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

        final CustomUserDetails userDetails = identity.getCustomUserDetails();
        final Role userRole = userDetails.getUser().getRole();
        if ((userRole.equals(Role.ROLE_CUSTOMER) && userDetails.getId().equals(customerId))
                || userRole.equals(Role.ROLE_ADMIN)) {
            return orderRepository.findAllByUserId(customerId, paginationRequest.toPageable())
                    .map(OrderMapper::toOrderDTO);
        }

        throw new AccessDeniedException("You cannot access order statistics");

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
