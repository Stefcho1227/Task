package org.example.taskproject.exception;

import jakarta.validation.ConstraintViolationException;
import org.example.taskproject.exception.custom.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Validation Error");
        errorBody.put("message", ex.getMessage());
        List<Map<String, String>> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> fe = new HashMap<>();
                    fe.put("field", fieldError.getField());
                    fe.put("message", fieldError.getDefaultMessage());
                    return fe;
                })
                .collect(Collectors.toList());
        errorBody.put("errors", fieldErrors);
        return errorBody;
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Constraint Violation");
        errorBody.put("message", "Validation failed for one or more fields.");

        List<Map<String, String>> violations = ex.getConstraintViolations().stream()
                .map(violation -> {
                    Map<String, String> violationInfo = new HashMap<>();
                    violationInfo.put("property", violation.getPropertyPath().toString());
                    violationInfo.put("invalidValue", violation.getInvalidValue() == null
                            ? "null"
                            : violation.getInvalidValue().toString());
                    violationInfo.put("message", violation.getMessage());
                    return violationInfo;
                })
                .collect(Collectors.toList());

        errorBody.put("violations", violations);
        return errorBody;
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", HttpStatus.NOT_FOUND.value());
        errorBody.put("error", "Not Found");
        errorBody.put("message", ex.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Type Mismatch");
        errorBody.put("message", "Invalid value for parameter: " + ex.getName());
        return errorBody;
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Malformed JSON Request");
        errorBody.put("message", ex.getMessage());
        return errorBody;
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Missing Request Parameter");
        errorBody.put("message", ex.getMessage());
        return errorBody;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherExceptions(Exception ex) {
        ResponseStatusException rse = null;
        if (ex instanceof ResponseStatusException) {
            rse = (ResponseStatusException) ex;
        } else if (ex.getCause() instanceof ResponseStatusException) {
            rse = (ResponseStatusException) ex.getCause();
        }
        if (rse != null) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("status", rse.getStatusCode().value());
            errorBody.put("error", "Not Found");
            errorBody.put("message", rse.getReason());
            return new ResponseEntity<>(errorBody, rse.getStatusCode());
        }
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", 500);
        errorBody.put("error", "Internal Server Error");
        errorBody.put("message", ex.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
