package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.payload.request.pagination.PaginatedFindAllRequest;
import com.example.demo.payload.request.pagination.PaginationRequest;
import org.springframework.data.domain.Page;

public interface OrderService {

    // get By OrderId
    OrderDTO findOrderById(Long id);

    // All Orders By Customer Id
    Page<OrderDTO> findAllOrdersByCustomerId(Long customerId, PaginationRequest paginationRequest);

    // All Orders With Date Interval
    Page<OrderDTO> findAllOrdersBetweenTwoDatesAndPagination(PaginatedFindAllRequest paginatedFindAllRequest);

}
