package com.fastkart.ecomm.FastKart.Ecomm.controller;

import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationRequest;
import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationResponse;
import com.fastkart.ecomm.FastKart.Ecomm.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return new ResponseEntity<AuthenticationResponse>(authenticationService.authenticate(request), HttpStatus.OK);
    }
}
