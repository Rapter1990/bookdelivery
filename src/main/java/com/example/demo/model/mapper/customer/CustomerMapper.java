package com.example.demo.model.mapper.customer;

import com.example.demo.model.User;
import com.example.demo.payload.response.customer.CustomerCreatedResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerMapper {


    public static CustomerCreatedResponse toCreatedResponse(User source) {

        if (source == null) {
            return null;
        }

        return CustomerCreatedResponse.builder()
                .id(source.getId())
                .fullName(source.getFullName())
                .username(source.getUsername())
                .email(source.getEmail())
                .build();
    }
}
