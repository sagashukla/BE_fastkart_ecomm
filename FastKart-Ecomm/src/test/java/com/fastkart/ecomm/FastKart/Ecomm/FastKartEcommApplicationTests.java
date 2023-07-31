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

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private RegisterService registerService;

	@MockBean
	private UserRepository userRepository;
	@Test
	void verifyValidAuthentication_Test() {
		String email = "sagar@gmail.com";

		Role role = Role
				.builder()
				.roleId(1)
				.roleType("SELLER")
				.build();

		User user = User
				.builder()
				.firstName("Sagar")
				.lastName("Shukla")
				.email("sagar@gmail.com")
				.password(new BCryptPasswordEncoder().encode("test123"))
				.role(role)
				.id(1)
				.build();

		AuthenticationRequest request = AuthenticationRequest
				.builder()
				.email(user.getEmail())
				.password("test123")
				.build();
		when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));
		assertNotNull(authenticationService.authenticate(request));
	}

	@Test
	void verifyInValidAuthentication_Test() {
		String email = "sagar@gmail.com";

		Role role = Role
				.builder()
				.roleId(1)
				.roleType("SELLER")
				.build();

		User user = User
				.builder()
				.firstName("Sagar")
				.lastName("Shukla")
				.email("sagar@gmail.com")
				.password(new BCryptPasswordEncoder().encode("test123"))
				.role(role)
				.id(1)
				.build();

		AuthenticationRequest request = AuthenticationRequest
				.builder()
				.email(user.getEmail())
				.password("wrongpassword")
				.build();
		when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));

		Throwable exception = assertThrows(UnableToLoginException.class, () -> authenticationService.authenticate(request));
		assertEquals("Invalid credentials", exception.getMessage());
	}

	@Test
	void verifyValidRegistration_Test(){
		Role role = Role
				.builder()
				.roleId(1)
				.roleType("BUYER")
				.build();

		User user = User
				.builder()
				.firstName("Sagar")
				.lastName("Shukla")
				.email("sagar@gmail.com")
				.password(new BCryptPasswordEncoder().encode("test123"))
				.role(role)
				.id(1)
				.build();

		RegisterRequest request = RegisterRequest
				.builder()
						.firstName(user.getFirstName())
								.lastName(user.getLastName())
										.email(user.getEmail())
												.password(user.getPassword())
														.roleType(role.getRoleType())
																.build();
		when(userRepository.save(user)).thenReturn(user);
		assertNotNull(registerService.register(request));
	}

	@Test
	void verifyInValidRegistrationUserAlreadyExist_Test(){
		Role role = Role
				.builder()
				.roleId(1)
				.roleType("BUYER")
				.build();

		User user = User
				.builder()
				.firstName("Sagar")
				.lastName("Shukla")
				.email("sagar@gmail.com")
				.password(new BCryptPasswordEncoder().encode("test123"))
				.role(role)
				.id(1)
				.build();

		RegisterRequest request = RegisterRequest
				.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password(user.getPassword())
				.roleType(role.getRoleType())
				.build();

		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

		Throwable exception = assertThrows(UnableToRegisterException.class, () -> registerService.register(request));
		assertEquals("User already exists", exception.getMessage());
	}

	@Test
	void verifyInValidRegistrationUserFirstNameNotProvided_Test(){
		Role role = Role
				.builder()
				.roleId(1)
				.roleType("BUYER")
				.build();

		User user = User
				.builder()
				.firstName(null)
				.lastName("Shukla")
				.email("sagar@gmail.com")
				.password(new BCryptPasswordEncoder().encode("test123"))
				.role(role)
				.id(1)
				.build();

		RegisterRequest request = RegisterRequest
				.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password(user.getPassword())
				.roleType(role.getRoleType())
				.build();

		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

		Throwable exception = assertThrows(UnableToRegisterException.class, () -> registerService.register(request));
		assertEquals("First name cannot be null or empty", exception.getMessage());
	}

	@Test
	void verifyInValidRegistrationUserLastNameNotProvided_Test(){
		Role role = Role
				.builder()
				.roleId(1)
				.roleType("BUYER")
				.build();

		User user = User
				.builder()
				.firstName("Sagar")
				.lastName(null)
				.email("sagar@gmail.com")
				.password(new BCryptPasswordEncoder().encode("test123"))
				.role(role)
				.id(1)
				.build();

		RegisterRequest request = RegisterRequest
				.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password(user.getPassword())
				.roleType(role.getRoleType())
				.build();

		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

		Throwable exception = assertThrows(UnableToRegisterException.class, () -> registerService.register(request));
		assertEquals("Last name cannot be null or empty", exception.getMessage());
	}

	@Test
	void verifyInValidRegistrationUserEmailNotProvided_Test(){
		Role role = Role
				.builder()
				.roleId(1)
				.roleType("BUYER")
				.build();

		User user = User
				.builder()
				.firstName("Sagar")
				.lastName("Shukla")
				.email(null)
				.password(new BCryptPasswordEncoder().encode("test123"))
				.role(role)
				.id(1)
				.build();

		RegisterRequest request = RegisterRequest
				.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password(user.getPassword())
				.roleType(role.getRoleType())
				.build();

		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

		Throwable exception = assertThrows(UnableToRegisterException.class, () -> registerService.register(request));
		assertEquals("Email cannot be null or empty", exception.getMessage());
	}

	@Test
	void verifyInValidRegistrationUserPasswordNotProvided_Test(){
		Role role = Role
				.builder()
				.roleId(1)
				.roleType("BUYER")
				.build();

		User user = User
				.builder()
				.firstName("Sagar")
				.lastName("Shukla")
				.email("sagar@gmail.com")
				.password(null)
				.role(role)
				.id(1)
				.build();

		RegisterRequest request = RegisterRequest
				.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password(user.getPassword())
				.roleType(role.getRoleType())
				.build();

		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

		Throwable exception = assertThrows(UnableToRegisterException.class, () -> registerService.register(request));
		assertEquals("Password cannot be null or empty", exception.getMessage());
	}

	@Test
	void verifyInValidRegistrationUserPasswordLessThanRequiredLength_Test(){
		Role role = Role
				.builder()
				.roleId(1)
				.roleType("BUYER")
				.build();

		User user = User
				.builder()
				.firstName("Sagar")
				.lastName("Shukla")
				.email("sagar@gmail.com")
				.password(new BCryptPasswordEncoder().encode("test"))
				.role(role)
				.id(1)
				.build();

		RegisterRequest request = RegisterRequest
				.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password("test")
				.roleType(role.getRoleType())
				.build();

		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

		Throwable exception = assertThrows(UnableToRegisterException.class, () -> registerService.register(request));
		assertEquals("Password should have at least 6 characters", exception.getMessage());
	}

	@Test
	void verifyInValidRegistrationUserRoleTypeNotProvided_Test(){
		Role role = Role
				.builder()
				.roleId(1)
				.roleType("BUYER")
				.build();

		User user = User
				.builder()
				.firstName("Sagar")
				.lastName("Shukla")
				.email("sagar@gmail.com")
				.password(new BCryptPasswordEncoder().encode("test123"))
				.role(role)
				.id(1)
				.build();

		RegisterRequest request = RegisterRequest
				.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password(user.getPassword())
				.roleType(null)
				.build();

		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

		Throwable exception = assertThrows(UnableToRegisterException.class, () -> registerService.register(request));
		assertEquals("Role cannot be null or empty", exception.getMessage());
	}

	@Test
	void verifyInValidRegistrationUserWrongRoleType_Test(){
		Role role = Role
				.builder()
				.roleId(1)
				.roleType("WRONGBUYER")
				.build();

		User user = User
				.builder()
				.firstName("Sagar")
				.lastName("Shukla")
				.email("sagar@gmail.com")
				.password(new BCryptPasswordEncoder().encode("test123"))
				.role(role)
				.id(1)
				.build();

		RegisterRequest request = RegisterRequest
				.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password(user.getPassword())
				.roleType(role.getRoleType())
				.build();

		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

		Throwable exception = assertThrows(UnableToRegisterException.class, () -> registerService.register(request));
		assertEquals("Role type can either be SELLER or BUYER", exception.getMessage());
	}
}
