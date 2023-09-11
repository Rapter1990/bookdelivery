package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.mapper.customer.CustomerMapper;
import com.example.demo.payload.request.CustomerCreateRequest;
import com.example.demo.payload.response.CustomResponse;
import com.example.demo.payload.response.customer.CustomerCreatedResponse;
import com.example.demo.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Creates a new {@link User} in Customer Role
     * Only admins can access.
     *
     * @param customerCreateRequest {@link CustomerCreateRequest}
     * @return Response entity of {@link CustomerCreatedResponse}
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public CustomResponse<CustomerCreatedResponse> createCustomer(
            @RequestBody @Valid final CustomerCreateRequest customerCreateRequest
    ) {

        final User createdUser = customerService.createCustomer(customerCreateRequest);
        final CustomerCreatedResponse createdResponse = CustomerMapper.toCreatedResponse(createdUser);

        return CustomResponse.ok(createdResponse);
    }

}
