package com.fastkart.ecomm.FastKart.Ecomm.controller;

import com.fastkart.ecomm.FastKart.Ecomm.dto.RegisterRequest;
import com.fastkart.ecomm.FastKart.Ecomm.service.RegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RegistrationControllerTest {

    @MockBean
    private RegisterService registerService;

    @Autowired
    private RegistrationController registrationController;
    @Test
    void register() {
        String success = "User has been registered successfully!";
        RegisterRequest request = RegisterRequest
                .builder()
                .firstName("test")
                .lastName("last")
                .email("test@email.com")
                .password("test123")
                .roleId(1)
                .build();

        when(registerService.register(request)).thenReturn("User has been registered successfully!");
        assertEquals(success, registrationController.register(request));
    }
}