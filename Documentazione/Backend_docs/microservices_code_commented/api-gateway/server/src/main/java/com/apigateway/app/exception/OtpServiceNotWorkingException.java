package com.apigateway.app.exception;

public class OtpServiceNotWorkingException extends RuntimeException {
    public OtpServiceNotWorkingException (String message) {
        super(message);
    }
}
