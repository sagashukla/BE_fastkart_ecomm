package com.fastkart.ecomm.FastKart.Ecomm.controller;


import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationResponse;
import com.fastkart.ecomm.FastKart.Ecomm.dto.RegisterRequest;
import com.fastkart.ecomm.FastKart.Ecomm.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/register")
@Slf4j
public class RegistrationController {
    @Autowired
    private RegisterService registerService;

    @PostMapping
    public String register(
            @RequestBody RegisterRequest request
    ){
        log.info("Inside register controller");
        log.info("Request: {}", request);
        return registerService.register(request);
    }
}
