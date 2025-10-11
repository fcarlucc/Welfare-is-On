package com.shopservice.app.exceptionHandler;

import com.shopservice.app.exception.PurchaseAlreadyMadeException;
import com.shopservice.app.exception.PurchaseNotFoundException;
import com.shopservice.app.exception.UserUpdateFailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * Constructs error response details for exceptions.
     *
     * @param e       The exception that occurred.
     * @param request The web request.
     * @param errors  Map to store error details.
     * @param status  HTTP status for the response.
     */
    private void ErrorResponse(Exception e, WebRequest request, Map<String, String> errors, HttpStatus status) {
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", String.valueOf(status.value()));
        errors.put("error", status.getReasonPhrase());
        errors.put("message", e.getLocalizedMessage());
        errors.put("path", request.getDescription(false));
    }

    /**
     * Handles PurchaseNotFoundException and constructs error response.
     *
     * @param ex      The PurchaseNotFoundException instance.
     * @param request The web request.
     * @return ResponseEntity with error details and HTTP status.
     */
    @ExceptionHandler(PurchaseNotFoundException.class)
    public ResponseEntity<Map<String, String>> PurchaseNotFoundExceptions(PurchaseNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles PurchaseAlreadyMadeException and constructs error response.
     *
     * @param ex      The PurchaseAlreadyMadeException instance.
     * @param request The web request.
     * @return ResponseEntity with error details and HTTP status.
     */
    @ExceptionHandler(PurchaseAlreadyMadeException.class)
    public ResponseEntity<Map<String, String>> PurchaseAlreadyMadeExceptions(PurchaseAlreadyMadeException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserUpdateFailException and constructs error response.
     *
     * @param ex      The UserUpdateFailException instance.
     * @param request The web request.
     * @return ResponseEntity with error details and HTTP status.
     */
    @ExceptionHandler(UserUpdateFailException.class)
    public ResponseEntity<Map<String, String>> UserUpdateFailExceptions(UserUpdateFailException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions and constructs a generic error response.
     *
     * @param e The exception that occurred.
     * @return ResponseEntity with generic error message and HTTP status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
    }
}
