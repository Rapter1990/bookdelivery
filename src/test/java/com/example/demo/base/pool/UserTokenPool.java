package com.example.demo.base.pool;

import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.jwt.JwtUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class UserTokenPool extends ObjectPool<String> {

    private final JwtUtils jwtUtils;

    private final User mockUser = User.builder()
            .id(1L)
            .username("customer_1")
            .email("customer@bookdelivery.com")
            .role(Role.ROLE_CUSTOMER)
            .fullName("customer_fullname")
            .build();

    private final CustomUserDetails customUserDetails = new CustomUserDetails(mockUser);

    @Override
    protected String create() {

        return jwtUtils.generateJwtToken(customUserDetails);
    }
}
