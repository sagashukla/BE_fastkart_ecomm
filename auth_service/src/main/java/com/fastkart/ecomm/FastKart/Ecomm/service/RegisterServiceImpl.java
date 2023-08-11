package com.fastkart.ecomm.FastKart.Ecomm.service;

import com.fastkart.ecomm.FastKart.Ecomm.common.Utils;
import com.fastkart.ecomm.FastKart.Ecomm.config.JwtService;
import com.fastkart.ecomm.FastKart.Ecomm.enums.RoleType;
import com.fastkart.ecomm.FastKart.Ecomm.dto.RegisterRequest;
import com.fastkart.ecomm.FastKart.Ecomm.entity.Role;
import com.fastkart.ecomm.FastKart.Ecomm.entity.User;
import com.fastkart.ecomm.FastKart.Ecomm.exception.UnableToRegisterException;
import com.fastkart.ecomm.FastKart.Ecomm.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
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
        log.info("Inside RegisterServiceImpl");
        log.info("Inside register()");
        validateRequest(registerRequest);
        Role role = Role.builder()
                .roleId(registerRequest.getRoleId())
                .roleType(getRoleType(registerRequest.getRoleId()))
                .build();
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .createdDate(System.currentTimeMillis())
                .build();

        Optional<User> userDb = userRepository.findByEmail(registerRequest.getEmail());
        if(userDb.isPresent()){
            log.info("User already exists");
            throw new UnableToRegisterException("User already exists");
        }
        User fetchedUser =  userRepository.save(user);
        log.info("User has been registered successfully: {}", fetchedUser);
        return "User has been registered successfully!";
    }

    private String getRoleType(Integer roleId) {
        if(roleId == 1){
            return RoleType.SELLER.toString();
        }
        else{
            return RoleType.BUYER.toString();
        }
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
        else if(Utils.validateId(request.getRoleId())){
            throw new UnableToRegisterException("Role cannot be null or empty");
        }
        else if(!(request.getRoleId() == 1 || request.getRoleId() == 2)) {
            throw new UnableToRegisterException("Role type can either be 1(SELLER) or 2(BUYER)");
        }
    }
}
