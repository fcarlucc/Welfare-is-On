package com.imageservice.app.exceptionHandler;

import com.imageservice.app.exception.ImageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Utility method to populate error response details.
     *
     * @param e       The exception that occurred.
     * @param request The web request associated with the exception.
     * @param errors  Map to store error details.
     * @param status  HTTP status to be set in the response.
     */
    private void ErrorResponse(Exception e, WebRequest request, Map<String, String> errors, HttpStatus status) {
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", String.valueOf(status.value()));
        errors.put("error", status.getReasonPhrase());
        errors.put("message", e.getLocalizedMessage());
        errors.put("path", request.getDescription(false));
    }

    /**
     * Exception handler for ImageNotFoundException.
     *
     * @param ex      The ImageNotFoundException instance.
     * @param request The web request associated with the exception.
     * @return ResponseEntity containing error details for client.
     */
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleImageNotFoundException(ImageNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.NOT_FOUND); // Set HTTP status as NOT_FOUND (404)
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    /**
     * Generic exception handler for all other exceptions.
     *
     * @param e The exception that occurred.
     * @return ResponseEntity with a generic error message and INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
    }
}
