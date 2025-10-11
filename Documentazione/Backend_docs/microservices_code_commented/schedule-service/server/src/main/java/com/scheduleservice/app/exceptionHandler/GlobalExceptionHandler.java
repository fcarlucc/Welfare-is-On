package com.scheduleservice.app.exceptionHandler;

import com.scheduleservice.app.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling various exceptions in the Schedule Service application.
 * Provides centralized exception handling across all RestController classes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Builds an error response with the given exception details and HTTP status.
     *
     * @param e       the exception that occurred
     * @param request the web request during which the exception occurred
     * @param errors  the map to hold error details
     * @param status  the HTTP status to be returned
     */
    private void ErrorResponse(Exception e, WebRequest request, Map<String, String> errors, HttpStatus status) {
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", String.valueOf(status.value()));
        errors.put("error", status.getReasonPhrase());
        errors.put("message", e.getLocalizedMessage());
        errors.put("path", request.getDescription(false));
    }

    /**
     * Handles SlotNotFoundException.
     *
     * @param ex      the exception that occurred
     * @param request the web request during which the exception occurred
     * @return a response entity with error details and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(SlotNotFoundException.class)
    public ResponseEntity<Map<String, String>> SlotIdNotFoundExceptions(SlotNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles BookingNotFoundException.
     *
     * @param ex      the exception that occurred
     * @param request the web request during which the exception occurred
     * @return a response entity with error details and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Map<String, String>> BookingIdNotFoundExceptions(BookingNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles AvailabilityNotFoundException.
     *
     * @param ex      the exception that occurred
     * @param request the web request during which the exception occurred
     * @return a response entity with error details and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(AvailabilityNotFoundException.class)
    public ResponseEntity<Map<String, String>> AvailabilityIdNotFoundExceptions(AvailabilityNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles BookingFailureException.
     *
     * @param ex      the exception that occurred
     * @param request the web request during which the exception occurred
     * @return a response entity with error details and HTTP status FORBIDDEN
     */
    @ExceptionHandler(BookingFailureException.class)
    public ResponseEntity<Map<String, String>> BookingFailureExceptions(BookingFailureException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles AvailabilitySetFailureException.
     *
     * @param ex      the exception that occurred
     * @param request the web request during which the exception occurred
     * @return a response entity with error details and HTTP status FORBIDDEN
     */
    @ExceptionHandler(AvailabilitySetFailureException.class)
    public ResponseEntity<Map<String, String>> AvailabilitySetFailureExceptions(AvailabilitySetFailureException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles SlotDurationWrongException.
     *
     * @param ex      the exception that occurred
     * @param request the web request during which the exception occurred
     * @return a response entity with error details and HTTP status FORBIDDEN
     */
    @ExceptionHandler(SlotDurationWrongException.class)
    public ResponseEntity<Map<String, String>> SlotDurationWrongExceptions(SlotDurationWrongException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles all other exceptions.
     *
     * @param e the exception that occurred
     * @return a response entity with a generic error message and HTTP status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
    }
}
