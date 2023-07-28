package com.fastkart.ecomm.FastKart.Ecomm.exception;

import com.fastkart.ecomm.FastKart.Ecomm.dto.LoginErrorResponse;
import com.fastkart.ecomm.FastKart.Ecomm.dto.RegistrationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<RegistrationErrorResponse> handleException(UnableToRegisterException exc){
        var errorResponse = RegistrationErrorResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exc.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler ResponseEntity<LoginErrorResponse> handleException(UnableToLoginException exc){
        var errorResponse = LoginErrorResponse
                .builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(exc.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
