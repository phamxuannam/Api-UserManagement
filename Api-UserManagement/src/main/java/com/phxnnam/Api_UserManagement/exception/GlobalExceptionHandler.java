package com.phxnnam.Api_UserManagement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<String> HandlingRuntimeException(RuntimeException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
