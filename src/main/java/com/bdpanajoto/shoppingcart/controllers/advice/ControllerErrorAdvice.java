package com.bdpanajoto.shoppingcart.controllers.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerErrorAdvice {

    public static final String ERROR_MESSAGE = "There was an error processing the request!";

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleGenericNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
