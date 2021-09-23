package com.webtech.urlshortener.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UserTO {

    public final Integer id;
    public final String name;
    public final String email;
    public final int urlsCreated;
    public final int maxUrls;

    @JsonCreator
    public UserTO(Integer id, String name, String email, int urlsCreated, int maxUrls) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.urlsCreated = urlsCreated;
        this.maxUrls = maxUrls;
    }

    public UserTO withUrlIncremented() {
        return new UserTO(id, name, email, urlsCreated + 1, maxUrls);
    }

    public UserTO withUrlDecremented() {
        return new UserTO(id, name, email, urlsCreated - 1, maxUrls);
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
