package com.location_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<Map<String , Object>> handleLocationNotFound(LocationNotFoundException ex){
        return buildResponse(HttpStatus.NOT_FOUND,ex.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationErrors(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        Map<String , Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status",HttpStatus.BAD_REQUEST.value());
        body.put("errors" , errors);
        return new ResponseEntity<>(body , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String , Object>> handleGeneral(Exception ex){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR , "Something went wrong");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String , Object>> buildResponse (HttpStatus status , String message){
        Map<String , Object> body = new LinkedHashMap<>();
        body.put("timestamp" , LocalDateTime.now());
        body.put("status" , status.value());
        body.put("message",message);
        return new ResponseEntity<>(body , status);
    }

}
