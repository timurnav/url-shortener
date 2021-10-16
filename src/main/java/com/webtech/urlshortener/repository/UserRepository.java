package com.webtech.urlshortener.repository;

import com.webtech.urlshortener.repository.entity.UserEntity;

import java.util.List;

public interface UserRepository {

    UserEntity save(UserEntity user);

    boolean deleteById(int userId);

    List<UserEntity> getAll();

    UserEntity getById(int userId);
}
