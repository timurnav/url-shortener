package com.webtech.urlshortener.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UserTO {

    public final Integer id;
    public final String name;
    public final String email;

    @JsonCreator
    public UserTO(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
