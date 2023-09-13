package com.example.demo.service.impl;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.mapper.order.OrderMapper;
import com.example.demo.payload.request.order.CreateOrderRequest;
import com.example.demo.payload.request.pagination.DateIntervalRequest;
import com.example.demo.payload.request.pagination.PaginatedFindAllRequest;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    // Can create a new OrderSaveService and define createOrder inside
    @Override
    public OrderDTO createOrder(CreateOrderRequest createOrderRequest) {
        return null;
    }

    @Override
    public OrderDTO findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderMapper::toOrderDTO)
                .orElseThrow();
    }

    @Override
    public Page<OrderDTO> findAllOrdersByCustomerId(Long customerId, PaginationRequest paginationRequest) {

        Pageable orderPageRequest = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());

        return orderRepository.findAllByUserId(customerId, orderPageRequest)
                .map(OrderMapper::toOrderDTO);
    }

    @Override
    public Page<OrderDTO> findAllOrdersBetweenTwoDatesAndPagination(PaginatedFindAllRequest paginatedFindAllRequest) {

        DateIntervalRequest dateIntervalRequest = paginatedFindAllRequest.getDateIntervalRequest();
        PaginationRequest paginationRequest = paginatedFindAllRequest.getPaginationRequest();
        Pageable orderPageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());

        return orderRepository.findAllByCreatedAtBetween(
                        dateIntervalRequest.getStartDate(),
                        dateIntervalRequest.getEndDate(),
                        orderPageable)
                .map(OrderMapper::toOrderDTO);

    }
}
