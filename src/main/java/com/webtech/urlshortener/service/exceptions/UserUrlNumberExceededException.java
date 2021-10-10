package com.webtech.urlshortener.service.exceptions;

import com.webtech.urlshortener.repository.UserEntity;

public class UserUrlNumberExceededException extends BaseApplicationException {

    public UserUrlNumberExceededException(UserEntity user) {
        super(String.format("User %d with roles %s urls number exceeded: %d/%d",
                user.getId(), user.getRoles(), user.getUrlsCreated(), user.getMaxUrls()));
    }
}
