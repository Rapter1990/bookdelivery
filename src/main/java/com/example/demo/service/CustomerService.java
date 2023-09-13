package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.payload.request.customer.CustomerCreateRequest;

public interface CustomerService {

    User createCustomer(CustomerCreateRequest customerCreateRequest);

    // get by customer id

    // get by customer email
}
