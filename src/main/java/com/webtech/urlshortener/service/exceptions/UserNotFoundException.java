package com.webtech.urlshortener.service.exceptions;

public class UserNotFoundException extends BaseApplicationException {

    public UserNotFoundException(int userId) {
        super("User by user id '" + userId + "' not found");
    }
}
