package com.bdpanajoto.shoppingcart.controllers.advice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerErrorAdvice {
    Logger logger = LoggerFactory.getLogger(ControllerErrorAdvice.class);
    public static final String ERROR_MESSAGE = "There was an error processing the request! ";

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleGenericNotFoundException(RuntimeException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(ERROR_MESSAGE + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
