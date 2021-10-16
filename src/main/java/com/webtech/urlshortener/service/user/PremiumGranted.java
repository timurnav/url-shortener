package com.webtech.urlshortener.service.user;

import com.webtech.urlshortener.repository.entity.UserEntity;
import com.webtech.urlshortener.repository.entity.UserRole;

public class PremiumGranted implements UserAdjustment {

    @Override
    public void apply(UserEntity user) {
        user.getRoles().add(UserRole.PREMIUM);
    }
}
