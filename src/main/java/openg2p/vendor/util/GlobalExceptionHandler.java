package openg2p.vendor.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        // Collecting all validation error messages
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });

        // Construct the response
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Exception Ocuured");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String datetime = LocalDateTime.now().format(formatter);

        // Collect all field errors
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                datetime,
                errors // Return all errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(Exception e) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String datetime = LocalDateTime.now().format(formatter);

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.FAILED_DEPENDENCY.value(),
                e.getMessage(),
                datetime,
                null
        );

        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(response);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalAccessException(Exception e) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String datetime = LocalDateTime.now().format(formatter);

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.FORBIDDEN.value(),
                e.getMessage(),
                datetime,
                null
        );

        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception e) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String datetime = LocalDateTime.now().format(formatter);

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred: " + e.getMessage(),
                datetime,
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
