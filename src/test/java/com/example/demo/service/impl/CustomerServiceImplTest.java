package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceTest;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.user.EmailAlreadyExistsException;
import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import com.example.demo.model.mapper.user.UserMapper;
import com.example.demo.payload.request.customer.CustomerCreateRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.RandomUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

class CustomerServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void givenValidCreateCustomerRequest_whenSuccess_thenReturnUserDto() {

        // Given
        CustomerCreateRequest mockRequest = CustomerCreateRequest.builder()
                .email(RandomUtil.generateRandomString().concat("@bookdelivery.com"))
                .password(RandomUtil.generateRandomString())
                .fullName(RandomUtil.generateRandomString())
                .username(RandomUtil.generateRandomString())
                .build();

        User user = User.builder()
                .id(1L)
                .email(mockRequest.getEmail())
                .fullName(mockRequest.getFullName())
                .username(mockRequest.getUsername())
                .role(Role.ROLE_CUSTOMER)
                .password(mockRequest.getPassword())
                .build();

        UserDTO userDTO = UserMapper.toDTO(user);

        // When
        Mockito.when(userRepository.existsByEmail(mockRequest.getEmail())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // Then
        UserDTO response = customerService.createCustomer(mockRequest);

        Assertions.assertEquals(userDTO, response);
        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(Mockito.anyString());
    }

    @Test
    void givenValidCreateCustomerRequest_whenEmailAlreadyExists_thenThrowEmailAlreadyExists() {

        // Given
        CustomerCreateRequest mockRequest = CustomerCreateRequest.builder()
                .email(RandomUtil.generateRandomString().concat("@bookdelivery.com"))
                .password(RandomUtil.generateRandomString())
                .fullName(RandomUtil.generateRandomString())
                .username(RandomUtil.generateRandomString())
                .build();

        // When
        Mockito.when(userRepository.existsByEmail(mockRequest.getEmail())).thenReturn(true);

        // Then
        Assertions.assertThrows(
                EmailAlreadyExistsException.class,
                () -> customerService.createCustomer(mockRequest)
        );
        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
        Mockito.verify(passwordEncoder, Mockito.never()).encode(Mockito.anyString());
    }
}