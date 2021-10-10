package com.webtech.urlshortener.service.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateUserTO {

    @NotBlank
    @Length(max = 100)
    public final String name;
    @NotNull
    @Email
    public final String email;
    // todo add custom validation
    public final String password;
    public final boolean isAdmin;

    @JsonCreator
    public CreateUserTO(String name, String email, String password, boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}
