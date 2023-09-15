package com.example.demo.service.impl;

import com.example.demo.dto.OrderReportDTO;
import com.example.demo.model.Order;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderRepository orderRepository;

    @Override
    public OrderReportDTO getOrderStatisticsByCustomer(Long customerId, PaginationRequest paginationRequest) {

        Page<Order> customerOrders = orderRepository.findAllByUserId(customerId, paginationRequest.toPageable());

        Integer year = customerOrders.getContent().get(0).getCreatedAt().getYear();
        String month = customerOrders.getContent().get(0).getCreatedAt().getMonth().toString();
        Integer totalOrderCount = orderRepository.countDistinctOrdersByUserId(customerId);

        Integer totalBookCount = totalBookCount(customerOrders);
        BigDecimal totalPrice = totalAllPrice(customerOrders);

        return new OrderReportDTO(month, year, totalOrderCount, totalBookCount, totalPrice);

    }

    @Override
    public OrderReportDTO getOrderStatistics(PaginationRequest paginationRequest) {

        Page<Order> customerOrders = orderRepository.findAll(paginationRequest.toPageable());

        Integer year = customerOrders.getContent().get(0).getCreatedAt().getYear();
        String month = customerOrders.getContent().get(0).getCreatedAt().getMonth().toString();
        Integer totalOrderCount = orderRepository.countDistinctOrders();

        Integer totalBookCount = totalBookCount(customerOrders);
        BigDecimal totalPrice = totalAllPrice(customerOrders);

        return new OrderReportDTO(month, year, totalOrderCount, totalBookCount, totalPrice);

    }


    private Integer totalBookCount(Page<Order> customerOrders){
        return customerOrders.stream()
                .map(order -> order.getOrderItems().size())
                .reduce(0, Integer::sum);
    }

    private BigDecimal totalAllPrice(Page<Order> customerOrders){
        return customerOrders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .map(orderItem -> orderItem.getBook().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
