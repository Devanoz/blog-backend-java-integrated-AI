package com.devano.blog_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ApiExceptionResponse> handler(ApiException e) {
        List<String> errorMessages = new ArrayList<>(Collections.singletonList(e.getMessage()));
        ApiExceptionResponse resposne = ApiExceptionResponse.builder().errorMessages(errorMessages).build();
        return ResponseEntity.status(e.getHttpStatus().value()).body(resposne);
    }
}
