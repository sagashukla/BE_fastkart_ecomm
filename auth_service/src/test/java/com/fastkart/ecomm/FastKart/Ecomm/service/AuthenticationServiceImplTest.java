package com.fastkart.ecomm.FastKart.Ecomm.service;

import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationRequest;
import com.fastkart.ecomm.FastKart.Ecomm.entity.Role;
import com.fastkart.ecomm.FastKart.Ecomm.entity.User;
import com.fastkart.ecomm.FastKart.Ecomm.exception.UnableToLoginException;
import com.fastkart.ecomm.FastKart.Ecomm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthenticationServiceImplTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RegisterService registerService;

    @MockBean
    private UserRepository userRepository;
    @Test
    void verifyValidAuthentication_Test() {
        String email = "sagar@gmail.com";

        Role role = Role
                .builder()
                .roleId(1)
                .roleType("SELLER")
                .build();

        User user = User
                .builder()
                .firstName("Sagar")
                .lastName("Shukla")
                .email("sagar@gmail.com")
                .password(new BCryptPasswordEncoder().encode("test123"))
                .role(role)
                .id(1)
                .build();

        AuthenticationRequest request = AuthenticationRequest
                .builder()
                .email(user.getEmail())
                .password("test123")
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));
        assertNotNull(authenticationService.authenticate(request));
    }

    @Test
    void verifyInValidAuthentication_Test() {
        String email = "sagar@gmail.com";

        Role role = Role
                .builder()
                .roleId(1)
                .roleType("SELLER")
                .build();

        User user = User
                .builder()
                .firstName("Sagar")
                .lastName("Shukla")
                .email("sagar@gmail.com")
                .password(new BCryptPasswordEncoder().encode("test123"))
                .role(role)
                .id(1)
                .build();

        AuthenticationRequest request = AuthenticationRequest
                .builder()
                .email(user.getEmail())
                .password("wrongpassword")
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));

        Throwable exception = assertThrows(UnableToLoginException.class, () -> authenticationService.authenticate(request));
        assertEquals("Invalid credentials", exception.getMessage());
    }
}