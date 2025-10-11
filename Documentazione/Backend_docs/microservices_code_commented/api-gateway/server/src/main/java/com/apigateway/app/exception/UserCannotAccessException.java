package com.apigateway.app.exception;

public class UserCannotAccessException extends RuntimeException{
    public UserCannotAccessException (String message) {
        super(message);
    }
}
