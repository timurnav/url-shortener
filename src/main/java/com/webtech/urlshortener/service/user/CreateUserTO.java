package com.webtech.urlshortener.service.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.webtech.urlshortener.service.validation.StrongPassword;
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
    @StrongPassword
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
