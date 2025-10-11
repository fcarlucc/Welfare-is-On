package com.userservice.app.exceptionHandler;

import com.userservice.app.exception.EmailNotFoundException;
import com.userservice.app.exception.UserIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * A global exception handler that provides centralized handling of exceptions
 * thrown across the application. This class is annotated with {@link RestControllerAdvice}
 * to handle exceptions in a RESTful context.
 *
 * <p>The exception handlers here are designed to catch specific exceptions and
 * return structured error responses with relevant details to the client. It also
 * provides a generic handler for any unforeseen exceptions.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Builds an error response map with details about the exception, including
     * timestamp, status, error message, and request path.
     *
     * @param e the exception that was thrown
     * @param request the web request during which the exception occurred
     * @param errors the map to populate with error details
     * @param status the HTTP status to associate with the error response
     */
    private void ErrorResponse(Exception e, WebRequest request, Map<String, String> errors, HttpStatus status) {
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", String.valueOf(status.value()));
        errors.put("error", status.getReasonPhrase());
        errors.put("message", e.getLocalizedMessage());
        errors.put("path", request.getDescription(false));
    }

    /**
     * Handles {@link EmailNotFoundException} exceptions and returns a structured
     * error response with HTTP status {@link HttpStatus#BAD_REQUEST}.
     *
     * @param ex the exception thrown
     * @param request the web request during which the exception occurred
     * @return a {@link ResponseEntity} containing the error details
     */
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Map<String, String>> EmailNotFoundExceptions(EmailNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link UserIdNotFoundException} exceptions and returns a structured
     * error response with HTTP status {@link HttpStatus#BAD_REQUEST}.
     *
     * @param ex the exception thrown
     * @param request the web request during which the exception occurred
     * @return a {@link ResponseEntity} containing the error details
     */
    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<Map<String, String>> UserIdNotFoundExceptions(UserIdNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions and returns a generic error message with HTTP status
     * {@link HttpStatus#INTERNAL_SERVER_ERROR}. This is a catch-all handler for unexpected
     * errors that do not have specific handlers.
     *
     * @param e the exception thrown
     * @return a {@link ResponseEntity} with a generic error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
    }
}
