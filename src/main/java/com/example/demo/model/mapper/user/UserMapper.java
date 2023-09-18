package com.example.demo.model.mapper.user;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import lombok.experimental.UtilityClass;

/**
 * Utility class for mapping operations related to {@link User} and {@link UserDTO}..
 */
@UtilityClass
public class UserMapper {

    /**
     * Converts a {@link User} object to a {@link UserDTO}.
     *
     * @param user The {@link User} object to be converted.
     * @return A {@link UserDTO} containing data from the source user object.
     */
    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    /**
     * Converts a {@link UserDTO} object to a {@link User} entity.
     *
     * @param userDTO The {@link UserDTO} object to be converted.
     * @return A {@link User} entity containing data from the source DTO.
     */
    public static User toUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .fullName(userDTO.getFullName())
                .build();
    }

}
