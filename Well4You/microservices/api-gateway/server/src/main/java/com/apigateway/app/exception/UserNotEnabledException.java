package com.apigateway.app.exception;

public class UserNotEnabledException extends RuntimeException{
    public UserNotEnabledException (String message) {
        super(message);
    }
}
