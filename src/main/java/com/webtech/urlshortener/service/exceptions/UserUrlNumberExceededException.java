package com.webtech.urlshortener.service.exceptions;

import com.webtech.urlshortener.service.dto.UserTO;

public class UserUrlNumberExceededException extends BaseApplicationException {
    public final UserTO user;

    public UserUrlNumberExceededException(UserTO user) {
        super("UserUrlNumberExceededException for " + user);
        this.user = user;
    }
}
