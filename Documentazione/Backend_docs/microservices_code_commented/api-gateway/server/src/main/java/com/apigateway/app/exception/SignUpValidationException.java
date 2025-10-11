package com.apigateway.app.exception;

public class SignUpValidationException extends RuntimeException{
    public SignUpValidationException (String message) {
        super(message);
    }
}
