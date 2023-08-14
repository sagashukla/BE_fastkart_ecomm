package com.fastkart.ecomm.FastKart.Ecomm.exception;

public class UnableToRegisterException extends RuntimeException{
    public UnableToRegisterException(String message) {
        super(message);
    }

    public UnableToRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
