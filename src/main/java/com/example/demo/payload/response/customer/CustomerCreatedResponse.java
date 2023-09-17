package com.example.demo.payload.response.customer;

import lombok.*;

/**
 * Represents a response object for customer creation, containing customer details.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreatedResponse {

    private Long id;

    private String fullName;

    private String username;

    private String email;

}
