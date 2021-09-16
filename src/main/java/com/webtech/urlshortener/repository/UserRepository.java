package com.webtech.urlshortener.repository;

import com.webtech.urlshortener.service.dto.UserTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {

    private final AtomicInteger counter = new AtomicInteger();
    private final Map<Integer, UserTO> users = new HashMap<>();

    public UserTO save(UserTO user) {
        if (user.id == null) {
            int id = counter.incrementAndGet();
            UserTO created = new UserTO(id, user.name, user.email);
            users.put(id, created);
            return created;
        } else {
            users.put(user.id, user);
            return user;
        }
    }

    public void deleteById(int userId) {
        users.remove(userId);
    }

    public List<UserTO> getAll() {
        return new ArrayList<>(users.values());
    }

    public UserTO getById(int userId) {
        return users.get(userId);
    }
}
