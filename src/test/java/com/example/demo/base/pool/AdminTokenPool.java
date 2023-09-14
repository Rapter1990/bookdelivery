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
public class AdminTokenPool extends ObjectPool<String> {

    private final JwtUtils jwtUtils;

    private final User mockAdmin = User.builder()
            .id(1L)
            .username("admin_1")
            .email("admin@bookdelivery.com")
            .role(Role.ROLE_ADMIN)
            .fullName("admin_fullname")
            .build();

    private final CustomUserDetails customUserDetails = new CustomUserDetails(mockAdmin);

    @Override
    protected String create() {
        return jwtUtils.generateJwtToken(customUserDetails);
    }
}
