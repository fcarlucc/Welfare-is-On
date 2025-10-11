package com.coachservice.app.exception;

public class DivisionNotFoundException extends RuntimeException {
    public DivisionNotFoundException(String message) {
        super(message);
    }
}
