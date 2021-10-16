package com.webtech.urlshortener.repository.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserEntity {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private Date registered;
    private int urlsCreated;
    private int maxUrls;
    private Set<UserRole> roles = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public int getUrlsCreated() {
        return urlsCreated;
    }

    public void setUrlsCreated(int urlsCreated) {
        this.urlsCreated = urlsCreated;
    }

    public int getMaxUrls() {
        return maxUrls;
    }

    public void setMaxUrls(int maxUrls) {
        this.maxUrls = maxUrls;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
