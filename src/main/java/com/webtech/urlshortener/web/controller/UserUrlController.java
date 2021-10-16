package com.webtech.urlshortener.web.controller;

import com.webtech.urlshortener.service.url.ShortenUrlRequest;
import com.webtech.urlshortener.service.url.ShortenUrlResponse;
import com.webtech.urlshortener.service.url.UserUrlService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/{userId}/urls")
public class UserUrlController {

    private final UserUrlService urlService;

    public UserUrlController(UserUrlService urlService) {
        this.urlService = urlService;
    }

    @PutMapping
    public ShortenUrlResponse shorten(@PathVariable int userId,
                                      @RequestBody ShortenUrlRequest shortenRequest) {
        return urlService.shorten(userId, shortenRequest);
    }

    @DeleteMapping("{urlId}")
    public void delete(@PathVariable int userId, @PathVariable int urlId) {
        urlService.delete(userId, urlId);
    }
}
