package com.shopservice.app.exception;

public class PurchaseAlreadyMadeException extends RuntimeException {
    public PurchaseAlreadyMadeException(String message) {
        super(message);
    }
}
