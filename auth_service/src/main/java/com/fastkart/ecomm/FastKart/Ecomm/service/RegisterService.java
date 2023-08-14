package com.fastkart.ecomm.FastKart.Ecomm.service;

import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationResponse;
import com.fastkart.ecomm.FastKart.Ecomm.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface RegisterService {
    public String register(RegisterRequest registerRequest);
}
