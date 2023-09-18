package com.example.demo.controller;

import com.example.demo.base.BaseControllerTest;
import com.example.demo.builder.UserBuilder;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import com.example.demo.model.mapper.customer.CustomerMapper;
import com.example.demo.model.mapper.user.UserMapper;
import com.example.demo.payload.request.customer.CustomerCreateRequest;
import com.example.demo.payload.response.CustomResponse;
import com.example.demo.payload.response.customer.CustomerCreatedResponse;
import com.example.demo.service.impl.CustomerServiceImpl;
import com.example.demo.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends BaseControllerTest {

    @MockBean
    private CustomerServiceImpl customerService;

    @Test
    void givenValidCustomerCreateRequest_whenAdminRole_thenReturnCustomerCreatedResponse() throws Exception {

        // Given
        CustomerCreateRequest mockRequest = CustomerCreateRequest.builder()
                .email(RandomUtil.generateRandomString().concat("@bookdelivery.com"))
                .password(RandomUtil.generateRandomString())
                .fullName(RandomUtil.generateRandomString())
                .username(RandomUtil.generateRandomString())
                .build();

        User user = new UserBuilder()
                .withId(1L)
                .withEmail(mockRequest.getEmail())
                .withFullName(mockRequest.getFullName())
                .withUsername(mockRequest.getUsername())
                .withRole(Role.ROLE_CUSTOMER)
                .build();

        UserDTO userDTO = UserMapper.toDTO(user);
        CustomerCreatedResponse customerCreatedResponse = CustomerMapper.toCreatedResponse(userDTO);

        // When
        Mockito.when(customerService.createCustomer(mockRequest)).thenReturn(userDTO);

        // Then
        CustomResponse<CustomerCreatedResponse> customResponseOfCustomerCreatedResponse = CustomResponse.created(customerCreatedResponse);

        mockMvc.perform(post("/api/v1/customers")
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response.id").value(customerCreatedResponse.getId()))
                .andExpect(jsonPath("$.response.fullName").value(customerCreatedResponse.getFullName()))
                .andExpect(jsonPath("$.response.username").value(customerCreatedResponse.getUsername()))
                .andExpect(jsonPath("$.response.email").value(customerCreatedResponse.getEmail()))
                .andExpect(jsonPath("$.isSuccess").value(customResponseOfCustomerCreatedResponse.getIsSuccess()))
                .andExpect(jsonPath("$.httpStatus").value(customResponseOfCustomerCreatedResponse.getHttpStatus().name()))
                .andExpect(jsonPath("$.time").isNotEmpty());
    }
}
