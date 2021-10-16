package com.webtech.urlshortener.service.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.webtech.urlshortener.repository.entity.UserEntity;

public class UserTO {

    public final int id;
    public final String name;
    public final String email;
    public final int urlsCreated;
    public final int maxUrls;

    @JsonCreator
    public UserTO(int id, String name, String email, int urlsCreated, int maxUrls) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.urlsCreated = urlsCreated;
        this.maxUrls = maxUrls;
    }

    public static UserTO fromEntity(UserEntity entity) {
        return new UserTO(entity.getId(), entity.getName(), entity.getEmail(),
                entity.getUrlsCreated(), entity.getMaxUrls());
    }

    @Override
    public String toString() {
        return "UserTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", urlsCreated=" + urlsCreated +
                ", maxUrls=" + maxUrls +
                '}';
    }
}
