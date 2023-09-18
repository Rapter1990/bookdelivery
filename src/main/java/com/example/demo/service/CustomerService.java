package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.payload.request.customer.CustomerCreateRequest;

/**
 * This interface defines a service for managing customer-related operations.
 */
public interface CustomerService {

    /**
     * Creates a new customer based on the provided customer creation request.
     *
     * @param customerCreateRequest The request containing customer information to be used for creation.
     * @return A {@link User} representing the newly created customer.
     */
    UserDTO createCustomer(CustomerCreateRequest customerCreateRequest);

}
