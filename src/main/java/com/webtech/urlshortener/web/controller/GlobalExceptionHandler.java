package com.webtech.urlshortener.web.controller;

import com.webtech.urlshortener.service.exceptions.BaseApplicationException;
import com.webtech.urlshortener.web.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseApplicationException.class)
    @ResponseStatus(NOT_ACCEPTABLE)
    public ResponseError handle(BaseApplicationException e) {
        logger.info("An error found: " + e.getMessage(), e);
        return ResponseError.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseError handle(Exception e) {
        logger.error("An error handled: " + e.getMessage(), e);
        return ResponseError.error(e.getMessage());
    }
}
