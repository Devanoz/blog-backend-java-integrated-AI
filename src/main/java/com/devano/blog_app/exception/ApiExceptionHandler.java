package com.devano.blog_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse> handler(ApiException e) {
        List<String> errorMessages = new ArrayList<>(Collections.singletonList(e.getMessage()));
        ApiExceptionResponse resposne = ApiExceptionResponse.builder().errorMessages(errorMessages).build();
        return ResponseEntity.status(e.getHttpStatus().value()).body(resposne);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handleHibernateValidation(MethodArgumentNotValidException e) {
        ApiExceptionResponse errorResponse = ApiExceptionResponse.builder().errorMessages(List.of(e.getMessage())).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handler(Exception e) {
        ApiExceptionResponse exception = ApiExceptionResponse.builder().errorMessages(List.of(e.getMessage())).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
    }
}
