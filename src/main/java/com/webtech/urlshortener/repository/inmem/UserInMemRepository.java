package com.webtech.urlshortener.repository.inmem;

import com.webtech.urlshortener.configuration.Resettable;
import com.webtech.urlshortener.repository.UserRepository;
import com.webtech.urlshortener.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserInMemRepository implements UserRepository, Resettable {

    private final AtomicInteger counter = new AtomicInteger();
    private final Map<Integer, UserEntity> users = new HashMap<>();

    public UserEntity save(UserEntity user) {
        if (user.getId() == null) {
            int id = counter.incrementAndGet();
            user.setId(id);
        }
        users.put(user.getId(), user);
        return user;
    }

    public boolean deleteById(int userId) {
        return users.remove(userId) != null;
    }

    public List<UserEntity> getAll() {
        return new ArrayList<>(users.values());
    }

    public UserEntity getById(int userId) {
        return users.get(userId);
    }

    @Override
    public void reset() {
        counter.set(0);
        users.clear();
    }
}
