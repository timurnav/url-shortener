package com.webtech.urlshortener.web.controller;

import com.webtech.urlshortener.service.dto.UserTO;
import com.webtech.urlshortener.service.exceptions.BaseApplicationException;
import com.webtech.urlshortener.service.exceptions.UserNotFoundException;
import com.webtech.urlshortener.service.exceptions.UserUrlNumberExceededException;
import com.webtech.urlshortener.web.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseError handle(UserNotFoundException e) {
        logger.info(e.getMessage(), e);
        return ResponseError.error(e.getMessage());
    }

    @ExceptionHandler(UserUrlNumberExceededException.class)
    @ResponseStatus(NOT_ACCEPTABLE)
    public ResponseError handle(UserUrlNumberExceededException e) {
        UserTO user = e.user;
        logger.info("user url max {} actual is {}. Unable to shorten url for user: {}",
                user.maxUrls, user.urlsCreated, user.id);
        return ResponseError.error("Number of max urls exceeded");
    }

    @ExceptionHandler(BaseApplicationException.class)
    @ResponseStatus(NOT_ACCEPTABLE)
    public ResponseError handle(BaseApplicationException e) {
        logger.warn("Unable to catch: " + e.getMessage(), e);
        return ResponseError.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseError handle(Exception e) {
        logger.error("An error handled: " + e.getMessage(), e);
        return ResponseError.error(e.getMessage());
    }
}
