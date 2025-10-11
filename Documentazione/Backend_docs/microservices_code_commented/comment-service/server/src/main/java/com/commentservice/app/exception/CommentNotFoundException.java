package com.commentservice.app.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException (String message) {
        super(message);
    }
}
