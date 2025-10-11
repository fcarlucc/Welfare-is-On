package com.shopservice.app.exception;

public class UserUpdateFailException extends RuntimeException {
    public UserUpdateFailException(String message) {
        super(message);
    }
}
