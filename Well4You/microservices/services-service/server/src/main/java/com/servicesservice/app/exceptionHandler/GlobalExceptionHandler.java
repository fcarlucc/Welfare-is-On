package com.servicesservice.app.exceptionHandler;

import com.servicesservice.app.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling various exceptions across the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Constructs error response details for exceptions.
     *
     * @param e       Exception that occurred
     * @param request WebRequest containing details of the request
     * @param errors  Map to store error details
     * @param status  HTTP status to be returned
     */
    private void ErrorResponse(Exception e, WebRequest request, Map<String, String> errors, HttpStatus status) {
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", String.valueOf(status.value()));
        errors.put("error", status.getReasonPhrase());
        errors.put("message", e.getLocalizedMessage());
        errors.put("path", request.getDescription(false));
    }

    /**
     * Handles ServiceNotFoundException and constructs appropriate response.
     *
     * @param ex      ServiceNotFoundException instance
     * @param request WebRequest containing details of the request
     * @return ResponseEntity with error details and HTTP status
     */
    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<Map<String, String>> ServiceNotFoundExceptions(ServiceNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles PillarNotFoundException and constructs appropriate response.
     *
     * @param ex      PillarNotFoundException instance
     * @param request WebRequest containing details of the request
     * @return ResponseEntity with error details and HTTP status
     */
    @ExceptionHandler(PillarNotFoundException.class)
    public ResponseEntity<Map<String, String>> PillarNotFoundExceptions(PillarNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles CategoryNotFoundException and constructs appropriate response.
     *
     * @param ex      CategoryNotFoundException instance
     * @param request WebRequest containing details of the request
     * @return ResponseEntity with error details and HTTP status
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> CategoryNotFoundExceptions(CategoryNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserIdNotFoundException and constructs appropriate response.
     *
     * @param ex      UserIdNotFoundException instance
     * @param request WebRequest containing details of the request
     * @return ResponseEntity with error details and HTTP status
     */
    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<Map<String, String>> UserIdNotFoundExceptions(UserIdNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ImageUploadingFailException and constructs appropriate response.
     *
     * @param ex      ImageUploadingFailException instance
     * @param request WebRequest containing details of the request
     * @return ResponseEntity with error details and HTTP status
     */
    @ExceptionHandler(ImageUploadingFailException.class)
    public ResponseEntity<Map<String, String>> ImageUploadingFailExceptions(ImageUploadingFailException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles MethodArgumentNotValidException for validation errors and constructs appropriate response.
     *
     * @param ex      MethodArgumentNotValidException instance
     * @param request WebRequest containing details of the request
     * @return ResponseEntity with validation errors and HTTP status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions and constructs a generic error response.
     *
     * @param e Exception that occurred
     * @return ResponseEntity with generic error message and HTTP status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
    }
}
