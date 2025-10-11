package com.likeservice.app.exceptionHandler;

import com.likeservice.app.exception.LikeAlreadySetException;
import com.likeservice.app.exception.LikeNotFoundException;
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
 * Global exception handler for handling specific exceptions and providing uniform error responses across the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Constructs error response details for exceptions.
     *
     * @param e       The exception that occurred.
     * @param request The current web request.
     * @param errors  Map to store error details.
     * @param status  HTTP status code for the response.
     */
    private void ErrorResponse(Exception e, WebRequest request, Map<String, String> errors, HttpStatus status) {
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", String.valueOf(status.value()));
        errors.put("error", status.getReasonPhrase());
        errors.put("message", e.getLocalizedMessage());
        errors.put("path", request.getDescription(false));
    }

    /**
     * Handles LikeNotFoundException and constructs an appropriate error response.
     *
     * @param ex      The LikeNotFoundException that occurred.
     * @param request The current web request.
     * @return ResponseEntity with error details and HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleLikeNotFoundException(LikeNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles LikeAlreadySetException and constructs an appropriate error response.
     *
     * @param ex      The LikeAlreadySetException that occurred.
     * @param request The current web request.
     * @return ResponseEntity with error details and HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(LikeAlreadySetException.class)
    public ResponseEntity<Map<String, String>> handleLikeAlreadySetException(LikeAlreadySetException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other unhandled exceptions and provides a generic error message.
     *
     * @param e The exception that occurred.
     * @return ResponseEntity with a generic error message and HTTP status INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
    }
}
