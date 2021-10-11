package com.webtech.urlshortener.web.controller;

import com.webtech.urlshortener.service.user.CreateUserTO;
import com.webtech.urlshortener.service.user.UserDataUpdateTO;
import com.webtech.urlshortener.service.user.UserService;
import com.webtech.urlshortener.service.user.UserTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserTO create(@Valid @RequestBody CreateUserTO createTO) {
        return service.create(createTO);
    }

    @PutMapping("/{userId}")
    public UserTO update(@PathVariable int userId,
                         @Valid @RequestBody UserDataUpdateTO adjustTO) {
        return service.adjust(userId, adjustTO);
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
