package com.apigateway.app.exceptionHandler;

import com.apigateway.app.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Handles specific exceptions and maps them to appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Utility method to prepare error response details.
     *
     * @param e       The exception that occurred.
     * @param request The WebRequest object.
     * @param errors  Map to store error details.
     * @param status  HTTP status for the error response.
     */
    private void ErrorResponse(Exception e, WebRequest request, Map<String, String> errors, HttpStatus status) {
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", String.valueOf(status.value()));
        errors.put("error", status.getReasonPhrase());
        errors.put("message", e.getLocalizedMessage());
        errors.put("path", request.getDescription(false));
    }

    /**
     * Handles HttpMessageNotReadableException.
     *
     * @param ex      The HttpMessageNotReadableException instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableExceptions(HttpMessageNotReadableException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserNotEnabledException.
     *
     * @param ex      The UserNotEnabledException instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
     */
    @ExceptionHandler(UserNotEnabledException.class)
    public ResponseEntity<Map<String, String>> handleUserNotEnabledExceptions(UserNotEnabledException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles BadCredentialsException.
     *
     * @param ex      The BadCredentialsException instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> badCredentialsExceptions(BadCredentialsException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles SignUpValidationException.
     *
     * @param ex      The SignUpValidationException instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
     */
    @ExceptionHandler(SignUpValidationException.class)
    public ResponseEntity<Map<String, String>> handleSignUpValidationExceptions(SignUpValidationException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles EmailNotFoundException.
     *
     * @param ex      The EmailNotFoundException instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
     */
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmailNotFoundExceptions(EmailNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles ErrorSendingEmailException.
     *
     * @param ex      The ErrorSendingEmailException instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
     */
    @ExceptionHandler(ErrorSendingEmailException.class)
    public ResponseEntity<Map<String, String>> handleErrorSendingEmailExceptions(ErrorSendingEmailException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles OtpServiceNotWorkingException.
     *
     * @param ex      The OtpServiceNotWorkingException instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
     */
    @ExceptionHandler(OtpServiceNotWorkingException.class)
    public ResponseEntity<Map<String, String>> handleOtpServiceNotWorkingExceptions(OtpServiceNotWorkingException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles UserIdNotFoundException.
     *
     * @param ex      The UserIdNotFoundException instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
     */
    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserIdNotFoundExceptions(UserIdNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles UserCannotAccessException.
     *
     * @param ex      The UserCannotAccessException instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
     */
    @ExceptionHandler(UserCannotAccessException.class)
    public ResponseEntity<Map<String, String>> UserCannotAccessExceptions(UserCannotAccessException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles InvalidOtpException.
     *
     * @param ex      The InvalidOtpException instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
     */
    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<Map<String, String>> handleInvalidOtpExceptions(InvalidOtpException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles ImageUploadingFailException.
     *
     * @param ex      The ImageUploadingFailException instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
     */
    @ExceptionHandler(ImageUploadingFailException.class)
    public ResponseEntity<Map<String, String>> ImageUploadingFailExceptions(ImageUploadingFailException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException.
     *
     * @param ex      The MethodArgumentNotValidException instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
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
     * Handles generic Exception.
     *
     * @param e       The Exception instance.
     * @param request The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception e, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        Throwable cause = e.getCause();
        if (cause != null) {
            System.out.println("Root cause: " + cause.getClass().getName() + ": " + cause.getMessage());
        }
        System.out.println("Exception = " + e.getMessage());
        ErrorResponse(e, request, errors, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
