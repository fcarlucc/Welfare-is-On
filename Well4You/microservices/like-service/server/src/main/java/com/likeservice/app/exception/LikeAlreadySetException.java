package com.likeservice.app.exception;

public class LikeAlreadySetException extends RuntimeException {
    public LikeAlreadySetException (String message) {
        super(message);
    }
}
