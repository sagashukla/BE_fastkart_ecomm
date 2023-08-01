package com.fastkart.ecomm.FastKart.Ecomm.controller;


import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationResponse;
import com.fastkart.ecomm.FastKart.Ecomm.dto.RegisterRequest;
import com.fastkart.ecomm.FastKart.Ecomm.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/register")
public class RegistrationController {
    @Autowired
    private RegisterService registerService;

    @PostMapping
    public String register(
            @RequestBody RegisterRequest request
    ){
        return registerService.register(request);
    }
}
