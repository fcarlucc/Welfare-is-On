package com.notificationservice.app.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the Notification Service application.
 * Handles exceptions thrown within controllers and provides appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all exceptions thrown within the application.
     *
     * @param e The exception that was thrown.
     * @return ResponseEntity with an HTTP 500 Internal Server Error status and a generic error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        System.out.println("\n\nerror = " + e.getMessage() + "\n\n");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
    }
}
