package com.fastkart.ecomm.FastKart.Ecomm.service;

import com.fastkart.ecomm.FastKart.Ecomm.config.JwtService;
import com.fastkart.ecomm.FastKart.Ecomm.config.enums.RoleType;
import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationResponse;
import com.fastkart.ecomm.FastKart.Ecomm.dto.RegisterRequest;
import com.fastkart.ecomm.FastKart.Ecomm.entity.Role;
import com.fastkart.ecomm.FastKart.Ecomm.entity.User;
import com.fastkart.ecomm.FastKart.Ecomm.exception.UnableToRegisterException;
import com.fastkart.ecomm.FastKart.Ecomm.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService{

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public RegisterServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        validateRequest(registerRequest);
        var role = Role.builder()
                .roleType(registerRequest.getRoleType())
                .build();
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    private void validateRequest(RegisterRequest request) {
        if(request.getEmail() == null){
            throw new UnableToRegisterException("Email cannot be null");
        }
        else if(request.getPassword() == null){
            throw new UnableToRegisterException("Password cannot be null");
        }
        else if(request.getPassword().length() < 5){
            throw new UnableToRegisterException("Password should have at least 6 characters");
        }
        else if(request.getFirstName() == null){
            throw new UnableToRegisterException("First name cannot be null");
        }
        else if(request.getLastName() == null){
            throw new UnableToRegisterException("Last name cannot be null");
        }
        else if(request.getRoleType() == null){
            throw new UnableToRegisterException("Role cannot be null");
        }
        else if(!(request.getRoleType().equals(RoleType.SELLER.toString()) || request.getRoleType().equals(RoleType.BUYER.toString()))) {
            throw new UnableToRegisterException("Role type can either be SELLER or BUYER");
        }
    }
}
