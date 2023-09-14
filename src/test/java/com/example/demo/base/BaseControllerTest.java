package com.example.demo.base;

import com.example.demo.base.pool.AdminTokenPool;
import com.example.demo.base.pool.UserTokenPool;
import com.example.demo.logging.entity.LogEntity;
import com.example.demo.logging.service.LogService;
import com.example.demo.logging.service.impl.LogServiceImpl;
import com.example.demo.model.User;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseControllerTest extends AbstractTestContainerConfiguration {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected CustomUserDetailsService customUserDetailsService;

    @MockBean
    protected LogServiceImpl logService;

    @Autowired
    protected JwtUtils jwtUtils;

    @Autowired
    protected UserTokenPool userTokenPool;

    @Autowired
    protected AdminTokenPool adminTokenPool;

    protected User mockUser;

    protected String mockUserToken;

    protected User mockAdmin;

    protected String mockAdminToken;


    @BeforeEach
    protected void initializeAuth() {
        this.mockUser = userTokenPool.getMockUser();
        this.mockUserToken = "Bearer ".concat(userTokenPool.checkOut());
        this.mockAdmin = adminTokenPool.getMockAdmin();
        this.mockAdminToken = "Bearer ".concat(adminTokenPool.checkOut());
        Mockito.when(customUserDetailsService.loadUserByUsername(mockUser.getEmail())).thenReturn(userTokenPool.getCustomUserDetails());
        Mockito.when(customUserDetailsService.loadUserByUsername(mockAdmin.getEmail())).thenReturn(adminTokenPool.getCustomUserDetails());
        Mockito.doNothing().when(logService).saveLogToDatabase(any(LogEntity.class));
    }

    @AfterEach
    protected void checkInPools() {
        userTokenPool.checkIn(mockUserToken);
        adminTokenPool.checkIn(mockAdminToken);
    }

}
