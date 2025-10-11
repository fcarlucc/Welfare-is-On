package com.apigateway.app.exception;

public class ErrorSendingEmailException extends RuntimeException{
    public ErrorSendingEmailException (String message) {
        super(message);
    }
}
