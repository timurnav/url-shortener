package com.webtech.urlshortener.web.controller;

import com.webtech.urlshortener.service.UserService;
import com.webtech.urlshortener.service.dto.UserTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserTO save(@RequestBody UserTO user) {
        return service.save(user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable int userId) {
        service.delete(userId);
    }

    @GetMapping("")
    public List<UserTO> all() {
        return service.fetchAll();
    }

    @GetMapping("/{userId}")
    public UserTO getOne(@PathVariable int userId) {
        return service.fetchOne(userId);
    }
}
