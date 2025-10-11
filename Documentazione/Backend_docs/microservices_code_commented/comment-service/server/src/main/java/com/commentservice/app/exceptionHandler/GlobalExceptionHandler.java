package com.commentservice.app.exceptionHandler;

import com.commentservice.app.exception.CommentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling specific exceptions across the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles CommentNotFoundException and returns a ResponseEntity with error details.
     * @param ex The CommentNotFoundException instance.
     * @param request The WebRequest object containing the request details.
     * @return ResponseEntity containing error details and HTTP status code.
     */
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Map<String, String>> CommentNotFoundExceptions(CommentNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general Exception and returns a ResponseEntity with a generic error message.
     * @param e The Exception instance.
     * @return ResponseEntity with HTTP status code 500 and a generic error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
    }

    /**
     * Constructs the error response map with timestamp, status, error, message, and path details.
     * @param e The Exception instance.
     * @param request The WebRequest object containing the request details.
     * @param errors The Map to populate with error details.
     * @param status The HttpStatus to set in the error response.
     */
    private void ErrorResponse(Exception e, WebRequest request, Map<String, String> errors, HttpStatus status) {
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", String.valueOf(status.value()));
        errors.put("error", status.getReasonPhrase());
        errors.put("message", e.getLocalizedMessage());
        errors.put("path", request.getDescription(false));
    }
}
