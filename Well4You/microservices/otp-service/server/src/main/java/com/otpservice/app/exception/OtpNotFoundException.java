package com.otpservice.app.exception;

public class OtpNotFoundException extends RuntimeException {
    public OtpNotFoundException(String message) {
        super(message);
    }
}
