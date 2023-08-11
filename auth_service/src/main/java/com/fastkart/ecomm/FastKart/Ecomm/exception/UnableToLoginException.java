package com.fastkart.ecomm.FastKart.Ecomm.exception;

public class UnableToLoginException extends RuntimeException{
    public UnableToLoginException(String message) {
        super(message);
    }

    public UnableToLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToLoginException(Throwable cause) {
        super(cause);
    }
}
