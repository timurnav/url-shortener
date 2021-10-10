package com.webtech.urlshortener.service.user;

import com.webtech.urlshortener.repository.UserEntity;
import com.webtech.urlshortener.repository.UserRole;

public class PremiumGranted implements UserAdjustment {

    @Override
    public void apply(UserEntity user) {
        user.getRoles().add(UserRole.PREMIUM);
    }
}
