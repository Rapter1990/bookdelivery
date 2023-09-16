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

        Map<String, List<Order>> ordersByMonthYear = customerOrders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().getYear() + "-" + order.getCreatedAt().getMonth()
                ));

        List<OrderReportDTO> reportDTOs = ordersByMonthYear.entrySet().stream()
                .map(entry -> {
                    String monthYearKey = entry.getKey();
                    List<Order> ordersForMonthYear = entry.getValue();
                    int totalOrderCount = ordersForMonthYear.size();

                    int totalBookCount = ordersForMonthYear.stream()
                            .mapToInt(order -> order.getOrderItems().size())
                            .sum();

                    BigDecimal totalPrice = ordersForMonthYear.stream()
                            .flatMap(order -> order.getOrderItems().stream())
                            .map(orderItem -> orderItem.getBook().getPrice())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    // Splitting the monthYearKey to extract month and year
                    String[] parts = monthYearKey.split("-");
                    String month = parts[1];
                    int year = Integer.parseInt(parts[0]);

                    return new OrderReportDTO(month, year, totalOrderCount, totalBookCount, totalPrice);
                })
                .toList();

        return new PageImpl<>(reportDTOs, customerOrders.getPageable(), customerOrders.getTotalElements());
    }

}
