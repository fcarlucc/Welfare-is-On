package com.userservice.app.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException (String message) {
        super(message);
    }
}
