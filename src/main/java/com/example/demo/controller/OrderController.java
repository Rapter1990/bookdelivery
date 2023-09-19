package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.enums.Role;
import com.example.demo.model.mapper.order.OrderMapper;
import com.example.demo.payload.request.order.CreateOrderRequest;
import com.example.demo.payload.request.pagination.PaginatedFindAllRequest;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.payload.response.CustomPageResponse;
import com.example.demo.payload.response.CustomResponse;
import com.example.demo.payload.response.order.OrderCreatedResponse;
import com.example.demo.payload.response.order.OrderGetBetweenDatesResponse;
import com.example.demo.payload.response.order.OrderGetByCustomerResponse;
import com.example.demo.payload.response.order.OrderGetResponse;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.OrderSaveService;
import com.example.demo.service.OrderService;
import com.example.demo.util.Identity;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;
    private final OrderSaveService orderSaveService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse<OrderCreatedResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {

        final OrderDTO orderDTO = orderSaveService.createOrder(createOrderRequest);
        final OrderCreatedResponse response = OrderMapper.toCreatedResponse(orderDTO);
        return CustomResponse.created(response);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')")
    public CustomResponse<OrderGetResponse> getOrderById(@PathVariable Long orderId) {

        final OrderDTO orderDTO = orderService.findOrderById(orderId);
        final OrderGetResponse response = OrderMapper.toGetResponse(orderDTO);
        return CustomResponse.ok(response);

    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')")
    public CustomResponse<CustomPageResponse<OrderGetByCustomerResponse>> getOrdersByCustomerId(
            @PathVariable Long customerId,
            @RequestBody PaginationRequest paginationRequest
    ) {

        final Page<OrderDTO> pageOfOrderDTOs = orderService
                .findAllOrdersByCustomerId(customerId, paginationRequest);

        final CustomPageResponse<OrderGetByCustomerResponse> response = OrderMapper
                    .toGetByCustomerResponse(pageOfOrderDTOs);
        return CustomResponse.ok(response);

    }

    @PostMapping("/between-dates")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomResponse<CustomPageResponse<OrderGetBetweenDatesResponse>> getOrdersBetweenTwoDates(
            @RequestBody PaginatedFindAllRequest paginatedFindAllRequest
    ) {
        final Page<OrderDTO> pageOfOrderDTOs = orderService
                .findAllOrdersBetweenTwoDatesAndPagination(paginatedFindAllRequest);
        final CustomPageResponse<OrderGetBetweenDatesResponse> response = OrderMapper
                .toGetBetweenDatesResponses(pageOfOrderDTOs);

        return CustomResponse.ok(response);
    }

}
