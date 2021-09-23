package com.webtech.urlshortener.web;

public class ResponseError {

    public String errorMessage;

    public static ResponseError error(String message) {
        ResponseError responseError = new ResponseError();
        responseError.errorMessage = message;
        return responseError;
    }
}
