package com.otpservice.app.exceptionHandler;

import com.otpservice.app.exception.OtpNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling exceptions thrown by the OTP service.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Constructs error response details for exceptions of type OtpNotFoundException.
     *
     * @param ex      The exception instance.
     * @param request The incoming web request.
     * @return ResponseEntity containing error details and HTTP status code.
     */
    @ExceptionHandler(OtpNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOtpNotFoundException(OtpNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        populateErrorDetails(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other unhandled exceptions with a generic error message.
     *
     * @param e The exception instance.
     * @return ResponseEntity with an internal server error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
    }

    /**
     * Populates error details into the provided error map.
     *
     * @param e       The exception instance.
     * @param request The incoming web request.
     * @param errors  The map to populate with error details.
     * @param status  The HTTP status to set in the error response.
     */
    private void populateErrorDetails(Exception e, WebRequest request, Map<String, String> errors, HttpStatus status) {
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", String.valueOf(status.value()));
        errors.put("error", status.getReasonPhrase());
        errors.put("message", e.getLocalizedMessage());
        errors.put("path", request.getDescription(false));
    }
}
