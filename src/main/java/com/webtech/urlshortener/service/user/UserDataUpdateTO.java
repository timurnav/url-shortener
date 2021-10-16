package com.webtech.urlshortener.service.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.webtech.urlshortener.repository.entity.UserEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDataUpdateTO implements UserAdjustment {

    @NotBlank
    @Length(max = 100)
    public final String name;
    @NotNull
    @Email
    public final String email;

    @JsonCreator
    public UserDataUpdateTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public void apply(UserEntity user) {
        user.setName(name);
        user.setEmail(email);
    }

    @Override
    public String toString() {
        return "UserDataUpdateTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
