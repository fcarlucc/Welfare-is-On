package com.coachservice.app.exceptionHandler;

import com.coachservice.app.exception.CoachNotFoundException;
import com.coachservice.app.exception.DivisionNotFoundException;
import com.coachservice.app.exception.PillarNotFoundException;
import com.coachservice.app.exception.UserIdNotFoundException;
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
 * Global exception handler for the application.
 * <p>
 * This class handles various exceptions thrown by controllers and provides a standardized error response format.
 * It uses {@link RestControllerAdvice} to globally handle exceptions across all {@code @RestController} instances
 * within the application.
 * </p>
 *
 * <p>Handled Exceptions:</p>
 * <ul>
 *     <li>{@link CoachNotFoundException}: Handles cases where a requested coach cannot be found.</li>
 *     <li>{@link PillarNotFoundException}: Handles cases where a requested pillar cannot be found.</li>
 *     <li>{@link DivisionNotFoundException}: Handles cases where a requested division cannot be found.</li>
 *     <li>{@link UserIdNotFoundException}: Handles cases where a user ID does not exist in the system.</li>
 *     <li>{@link Exception}: A catch-all handler for any other unhandled exceptions, returning a generic error message.</li>
 * </ul>
 *
 * <p>Each exception is mapped to an HTTP response with an appropriate status code and a detailed error message,
 * including timestamp, status, error reason, message, and request path.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Constructs an error response map with details about the exception and request.
     *
     * @param e the exception that was thrown
     * @param request the current web request
     * @param errors a map to populate with error details
     * @param status the HTTP status to set for the response
     */
    private void ErrorResponse(Exception e, WebRequest request, Map<String, String> errors, HttpStatus status) {
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", String.valueOf(status.value()));
        errors.put("error", status.getReasonPhrase());
        errors.put("message", e.getLocalizedMessage());
        errors.put("path", request.getDescription(false));
    }

    /**
     * Handles {@link CoachNotFoundException} and returns a {@link ResponseEntity} with an appropriate error response.
     *
     * @param ex the exception that was thrown
     * @param request the current web request
     * @return a {@link ResponseEntity} containing the error details and HTTP status 400 Bad Request
     */
    @ExceptionHandler(CoachNotFoundException.class)
    public ResponseEntity<Map<String, String>> CoachNotFoundExceptions(CoachNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link PillarNotFoundException} and returns a {@link ResponseEntity} with an appropriate error response.
     *
     * @param ex the exception that was thrown
     * @param request the current web request
     * @return a {@link ResponseEntity} containing the error details and HTTP status 400 Bad Request
     */
    @ExceptionHandler(PillarNotFoundException.class)
    public ResponseEntity<Map<String, String>> PillarNotFoundExceptions(PillarNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link DivisionNotFoundException} and returns a {@link ResponseEntity} with an appropriate error response.
     *
     * @param ex the exception that was thrown
     * @param request the current web request
     * @return a {@link ResponseEntity} containing the error details and HTTP status 400 Bad Request
     */
    @ExceptionHandler(DivisionNotFoundException.class)
    public ResponseEntity<Map<String, String>> DivisionNotFoundExceptions(DivisionNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link UserIdNotFoundException} and returns a {@link ResponseEntity} with an appropriate error response.
     *
     * @param ex the exception that was thrown
     * @param request the current web request
     * @return a {@link ResponseEntity} containing the error details and HTTP status 400 Bad Request
     */
    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<Map<String, String>> UserIdNotFoundExceptions(UserIdNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse(ex, request, errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any other exceptions not explicitly handled by specific exception handlers.
     *
     * @param e the exception that was thrown
     * @return a {@link ResponseEntity} containing a generic error message and HTTP status 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
    }
}
