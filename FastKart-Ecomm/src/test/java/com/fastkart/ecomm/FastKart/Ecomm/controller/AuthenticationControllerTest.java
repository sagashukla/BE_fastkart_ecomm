package com.fastkart.ecomm.FastKart.Ecomm.controller;

import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationRequest;
import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationResponse;
import com.fastkart.ecomm.FastKart.Ecomm.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationController authenticationController;
    @Test
    void authenticate() {
        AuthenticationResponse response = AuthenticationResponse
                .builder()
                .token("token")
                .build();
        AuthenticationRequest request = AuthenticationRequest
                .builder()
                .email("test@gmail.com")
                .password("test@123")
                .build();

        when(authenticationService.authenticate(request)).thenReturn(response);

        assertEquals("token", authenticationController.authenticate(request).getBody().getToken());
    }
}