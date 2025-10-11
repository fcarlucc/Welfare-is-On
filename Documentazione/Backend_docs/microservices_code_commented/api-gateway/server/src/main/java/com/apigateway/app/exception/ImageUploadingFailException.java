package com.apigateway.app.exception;

public class ImageUploadingFailException extends RuntimeException {
    public ImageUploadingFailException(String message) {
        super(message);
    }
}
