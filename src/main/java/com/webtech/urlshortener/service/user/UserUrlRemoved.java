package com.webtech.urlshortener.service.user;

import com.webtech.urlshortener.repository.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserUrlRemoved implements UserAdjustment {

    private static final Logger logger = LoggerFactory.getLogger(UserUrlRemoved.class);

    @Override
    public void apply(UserEntity user) {
        int newValue = user.getUrlsCreated() - 1;
        if (newValue < 0) {
            logger.warn("User {} has negative urls count {}", user.getId(), newValue);
        }
        user.setUrlsCreated(newValue);
    }
}
