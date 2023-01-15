package com.zenicius.personcontrol.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> NotFoundException(NotFoundException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }
}
