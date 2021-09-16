package com.webtech.urlshortener.service;

import com.webtech.urlshortener.repository.UserRepository;
import com.webtech.urlshortener.service.dto.UserTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserTO save(UserTO user) {
        return repository.save(user);
    }

    public UserTO urlAdded(int userId) {
        UserTO user = repository.getById(userId);
        if (user.urlsCreated == user.maxUrls) {
            throw new RuntimeException("Urls number exceeded, max is " + user.maxUrls);
        }
        return repository.save(user.withUrlIncremented());
    }

    public UserTO urlRemoved(int userId) {
        UserTO user = repository.getById(userId);
        return repository.save(user.withUrlDecremented());
    }

    public void delete(int userId) {
        repository.deleteById(userId);
    }

    public List<UserTO> fetchAll() {
        return repository.getAll();
    }

    public UserTO fetchOne(int userId) {
        return repository.getById(userId);
    }
}
