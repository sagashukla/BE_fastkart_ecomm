package com.fastkart.ecomm.FastKart.Ecomm.service;

import com.fastkart.ecomm.FastKart.Ecomm.common.Utils;
import com.fastkart.ecomm.FastKart.Ecomm.config.JwtService;
import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationRequest;
import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationResponse;
import com.fastkart.ecomm.FastKart.Ecomm.entity.User;
import com.fastkart.ecomm.FastKart.Ecomm.exception.UnableToLoginException;
import com.fastkart.ecomm.FastKart.Ecomm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Map<String, String> clamis = new HashMap<>();
        validateRequest(authenticationRequest);

        Optional<User> user = userRepository.findByEmail(authenticationRequest.getEmail());

        if(user.isEmpty()){
            throw new UnableToLoginException("Invalid credentials");
        }
        else {
            validatePassword(user.get().getPassword(), authenticationRequest.getPassword());
            User userDb = user.get();
            clamis.put("role", userDb.getRole().getRoleType());
            var jwtToken = jwtService.generateToken(clamis, user.get());
            AuthenticationResponse authenticationResponse = AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .build();
            return authenticationResponse;
        }
    }

    private void validatePassword(String passwordDb, String passwordReq) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!bCryptPasswordEncoder.matches(passwordReq, passwordDb)){
            throw new UnableToLoginException("Invalid credentials");
        }
    }

    private void validateRequest(AuthenticationRequest request) {
        if(Utils.validateString(request.getEmail())){
            throw new UnableToLoginException("Email cannot be null or empty");
        }
        else if(Utils.validateString(request.getPassword())){
            throw new UnableToLoginException("Password cannot be null or empty");
        }
    }
}
