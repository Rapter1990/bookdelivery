package com.example.demo.service.impl;

import com.example.demo.dto.OrderReportDTO;
import com.example.demo.model.Order;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderRepository orderRepository;

    @Override
    public Page<OrderReportDTO> getOrderStatisticsByCustomer(Long customerId, PaginationRequest paginationRequest) {

        Page<Order> customerOrders = orderRepository.findAllByUserId(customerId, paginationRequest.toPageable());

        return calculateTotalOrderCountByMonth(customerOrders);

    }


    @Override
    public Page<OrderReportDTO> getOrderStatistics(PaginationRequest paginationRequest) {

        Page<Order> customerOrders = orderRepository.findAll(paginationRequest.toPageable());

        return calculateTotalOrderCountByMonth(customerOrders);

    }

    private Page<OrderReportDTO> calculateTotalOrderCountByMonth(Page<Order> customerOrders) {
        Map<String, Integer> totalOrderCountByMonth = customerOrders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().getYear() + "-" + order.getCreatedAt().getMonth(),
                        Collectors.mapping(Order::getId, Collectors.collectingAndThen(Collectors.toSet(), Set::size))
                ));

        List<OrderReportDTO> reportDTOs = customerOrders.get().map(order -> {
            String month = order.getCreatedAt().getMonth().toString();
            Integer year = order.getCreatedAt().getYear();

            String monthYearKey = year + "-" + month;
            Integer totalOrderCount = totalOrderCountByMonth.getOrDefault(monthYearKey, 0);

            Integer totalBookCount = order.getOrderItems().size();
            BigDecimal totalPrice = order.getOrderItems().stream()
                    .map(orderItem -> orderItem.getBook().getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new OrderReportDTO(month, year, totalOrderCount, totalBookCount, totalPrice);
        }).toList();

        return new PageImpl<>(reportDTOs, customerOrders.getPageable(), customerOrders.getTotalElements());
    }

}
