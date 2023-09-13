package com.example.demo.dto;

import com.example.demo.model.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {

    private Long id;
    private String fullName;

}
