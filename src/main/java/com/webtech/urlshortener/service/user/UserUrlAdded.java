package com.webtech.urlshortener.service.user;

import com.webtech.urlshortener.repository.entity.UserEntity;
import com.webtech.urlshortener.repository.entity.UserRole;
import com.webtech.urlshortener.service.exceptions.UserUrlNumberExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserUrlAdded implements UserAdjustment {

    private static final Logger logger = LoggerFactory.getLogger(UserUrlAdded.class);

    @Override
    public void apply(UserEntity user) {
        int newValue = user.getUrlsCreated() + 1;
        int maxUrls = user.getMaxUrls();
        if (user.getRoles().contains(UserRole.PREMIUM)) {
            maxUrls *= 2;
        }
        if (newValue > maxUrls) {
            if (user.getRoles().contains(UserRole.ADMIN)) {
                logger.info("User {} has an extra url {}/{}", user.getId(), user.getUrlsCreated(), maxUrls);
            } else {
                throw new UserUrlNumberExceededException(user);
            }
        }
        user.setUrlsCreated(newValue);
    }
}
