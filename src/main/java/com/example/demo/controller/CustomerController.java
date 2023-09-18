package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.model.mapper.customer.CustomerMapper;
import com.example.demo.payload.request.customer.CustomerCreateRequest;
import com.example.demo.payload.response.CustomResponse;
import com.example.demo.payload.response.customer.CustomerCreatedResponse;
import com.example.demo.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse<CustomerCreatedResponse> createCustomer(
            @RequestBody @Valid final CustomerCreateRequest customerCreateRequest
    ) {

        final UserDTO createdUser = customerService.createCustomer(customerCreateRequest);
        final CustomerCreatedResponse createdResponse = CustomerMapper.toCreatedResponse(createdUser);

        return CustomResponse.created(createdResponse);
    }

}
