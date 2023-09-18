package com.example.demo.model.mapper.customer;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.payload.response.customer.CustomerCreatedResponse;
import lombok.experimental.UtilityClass;

/**
 * Utility class for mapping operations related to customer data.
 */
@UtilityClass
public class CustomerMapper {

    /**
     * Converts a {@link User} object to a {@link CustomerCreatedResponse}.
     *
     * @param source The source {@link User} object to be converted.
     * @return A {@link CustomerCreatedResponse} containing data from the source object.
     */
    public static CustomerCreatedResponse toCreatedResponse(UserDTO source) {

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
