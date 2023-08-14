package com.fastkart.ecomm.FastKart.Ecomm.controller;

import com.fastkart.ecomm.FastKart.Ecomm.config.JwtService;
import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationRequest;
import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationResponse;
import com.fastkart.ecomm.FastKart.Ecomm.entity.TokenValidationResponse;
import com.fastkart.ecomm.FastKart.Ecomm.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){

        log.info("Inside authentication controller");
        log.info("Request: {}", request);
        return new ResponseEntity<AuthenticationResponse>(authenticationService.authenticate(request), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/validatetoken")
    public ResponseEntity<TokenValidationResponse> validateToken(HttpServletRequest request){
        log.info("Inside authentication controller");
        log.info("Request: {}", request);
        String email = (String) request.getAttribute("email");
        String role = (String) request.getAttribute("role");

        return ResponseEntity.ok(TokenValidationResponse.builder().status("OK").methodType(HttpMethod.GET.name())
                .email(email)
                .roleType(role)
                .isAuthenticated(true)
                .build()
        );
    }
}
