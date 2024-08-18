package com.financeiro.gestao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        logger.error("Resource not found: {}", ex.getMessage());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", "Resource Not Found");
        responseBody.put("message", ex.getMessage());
        responseBody.put("status", ex.getStatus().value());

        return new ResponseEntity<>(responseBody, ex.getStatus());
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Object> handleBusinessRuleException(BusinessRuleException ex, WebRequest request) {
        logger.error("Business rule violation: {}", ex.getMessage());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", "Business Rule Violation");
        responseBody.put("message", ex.getMessage());
        responseBody.put("status", ex.getStatus().value());

        return new ResponseEntity<>(responseBody, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("An unexpected error occurred: {}", ex.getMessage());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", "Internal Server Error");
        responseBody.put("message", ex.getMessage());
        responseBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
