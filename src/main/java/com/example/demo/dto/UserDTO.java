package com.example.demo.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing user information.
 */
@Getter
@Builder
@EqualsAndHashCode
public class UserDTO {

    private Long id;
    private String fullName;
    private String username;
    private String email;

}
