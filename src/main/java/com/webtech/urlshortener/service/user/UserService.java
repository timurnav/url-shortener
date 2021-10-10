package com.webtech.urlshortener.service.user;

import com.webtech.urlshortener.repository.UserEntity;
import com.webtech.urlshortener.repository.UserRepository;
import com.webtech.urlshortener.repository.UserRole;
import com.webtech.urlshortener.service.exceptions.UserNotFoundException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository repository;
    private final int maxUrlsDefault;

    public UserService(UserRepository repository,
                       int maxUrlsDefault) {

        this.repository = repository;
        this.maxUrlsDefault = maxUrlsDefault;
    }

    public UserTO create(CreateUserTO createTo) {
        UserEntity user = new UserEntity();
        user.setName(createTo.name);
        user.setEmail(createTo.email);
        user.setPassword(createTo.password);
        user.setMaxUrls(maxUrlsDefault);
        user.setUrlsCreated(0);
        user.setRegistered(Instant.now());
        if (createTo.isAdmin) {
            user.getRoles().add(UserRole.ADMIN);
        }
        UserEntity saved = repository.save(user);
        return UserTO.fromEntity(saved);
    }

    public UserTO adjust(int userId, UserAdjustment adjustment) {
        UserEntity existing = getExistingUser(userId);
        adjustment.apply(existing);
        UserEntity saved = repository.save(existing);
        return UserTO.fromEntity(saved);
    }

    public void delete(int userId) {
        if (repository.deleteById(userId)) {
            return;
        }
        throw new UserNotFoundException(userId);
    }

    public List<UserTO> fetchAll() {
        return repository.getAll().stream()
                .map(UserTO::fromEntity)
                .collect(Collectors.toList());
    }

    public UserTO fetchOne(int userId) {
        UserEntity entity = getExistingUser(userId);
        return UserTO.fromEntity(entity);
    }

    private UserEntity getExistingUser(int id) {
        UserEntity entity = repository.getById(id);
        if (entity == null) {
            throw new UserNotFoundException(id);
        }
        return entity;
    }
}
