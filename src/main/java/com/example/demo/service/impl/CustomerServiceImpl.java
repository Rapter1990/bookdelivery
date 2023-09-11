package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import com.example.demo.payload.request.CustomerCreateRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    /**
     * Creates a new {@link User} in Customer Role
     *
     * @param request {@link CustomerCreateRequest}
     * @return The created User.
     */
    @Override
    public User createCustomer(CustomerCreateRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_CUSTOMER)
                .build();

        return userRepository.save(user);
    }


}
