package com.webtech.urlshortener.service.user;

import com.webtech.urlshortener.repository.UserEntity;

public interface UserAdjustment {

    void apply(UserEntity user);
}
