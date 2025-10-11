package com.scheduleservice.app.exception;

public class BookingFailureException extends RuntimeException {
    public BookingFailureException(String message) {
        super(message);
    }
}
