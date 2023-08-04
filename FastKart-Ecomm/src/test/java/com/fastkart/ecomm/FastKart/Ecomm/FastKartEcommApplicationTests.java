package com.fastkart.ecomm.FastKart.Ecomm;

import com.fastkart.ecomm.FastKart.Ecomm.dto.AuthenticationRequest;
import com.fastkart.ecomm.FastKart.Ecomm.dto.RegisterRequest;
import com.fastkart.ecomm.FastKart.Ecomm.entity.Role;
import com.fastkart.ecomm.FastKart.Ecomm.entity.User;
import com.fastkart.ecomm.FastKart.Ecomm.exception.UnableToLoginException;
import com.fastkart.ecomm.FastKart.Ecomm.exception.UnableToRegisterException;
import com.fastkart.ecomm.FastKart.Ecomm.repository.UserRepository;
import com.fastkart.ecomm.FastKart.Ecomm.service.AuthenticationService;
import com.fastkart.ecomm.FastKart.Ecomm.service.RegisterService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class FastKartEcommApplicationTests {

}
