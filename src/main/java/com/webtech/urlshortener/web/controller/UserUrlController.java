package com.webtech.urlshortener.web.controller;

import com.webtech.urlshortener.service.UrlService;
import com.webtech.urlshortener.service.dto.ShortenUrlRequest;
import com.webtech.urlshortener.service.dto.ShortenedUrlTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/{userId}/urls")
public class UserUrlController {

    private final UrlService urlService;

    public UserUrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ShortenedUrlTO shorten(
            @PathVariable int userId,
            @RequestBody ShortenUrlRequest shortenRequest) {
        return urlService.shorten(userId, shortenRequest);
    }

    @DeleteMapping("{urlId}")
    public void delete(@PathVariable int userId, @PathVariable int urlId) {
        urlService.delete(userId, urlId);
    }
}
