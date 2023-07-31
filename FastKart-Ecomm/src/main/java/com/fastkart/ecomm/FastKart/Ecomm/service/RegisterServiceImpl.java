package com.fastkart.ecomm.FastKart.Ecomm.service;

import com.fastkart.ecomm.FastKart.Ecomm.common.Utils;
import com.fastkart.ecomm.FastKart.Ecomm.config.JwtService;
import com.fastkart.ecomm.FastKart.Ecomm.enums.RoleType;
import com.fastkart.ecomm.FastKart.Ecomm.dto.RegisterRequest;
import com.fastkart.ecomm.FastKart.Ecomm.entity.Role;
import com.fastkart.ecomm.FastKart.Ecomm.entity.User;
import com.fastkart.ecomm.FastKart.Ecomm.exception.UnableToRegisterException;
import com.fastkart.ecomm.FastKart.Ecomm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterServiceImpl implements RegisterService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public RegisterServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String register(RegisterRequest registerRequest) {
        validateRequest(registerRequest);
        Role role = Role.builder()
                .roleType(registerRequest.getRoleType())
                .build();
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .build();

        Optional<User> userDb = userRepository.findByEmail(registerRequest.getEmail());
        if(userDb.isPresent()){
            throw new UnableToRegisterException("User already exists");
        }
        userRepository.save(user);
        return "User has been registered successfully!";
    }

    private void validateRequest(RegisterRequest request) {
        if(Utils.validateString(request.getEmail())){
            throw new UnableToRegisterException("Email cannot be null or empty");
        }
        else if(Utils.validateString(request.getPassword())){
            throw new UnableToRegisterException("Password cannot be null or empty");
        }
        else if(Utils.validatePassword(request.getPassword())){
            throw new UnableToRegisterException("Password should have at least 6 characters");
        }
        else if(Utils.validateString(request.getFirstName())){
            throw new UnableToRegisterException("First name cannot be null or empty");
        }
        else if(Utils.validateString(request.getLastName())){
            throw new UnableToRegisterException("Last name cannot be null or empty");
        }
        else if(Utils.validateString(request.getRoleType())){
            throw new UnableToRegisterException("Role cannot be null or empty");
        }
        else if(!(request.getRoleType().equals(RoleType.SELLER.toString()) || request.getRoleType().equals(RoleType.BUYER.toString()))) {
            throw new UnableToRegisterException("Role type can either be SELLER or BUYER");
        }
    }
}
