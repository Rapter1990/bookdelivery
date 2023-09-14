package com.example.demo.model.mapper.user;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .build();
    }

    public static User toUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .fullName(userDTO.getFullName())
                .build();
    }
}
