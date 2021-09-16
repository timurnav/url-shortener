package com.webtech.urlshortener.web.controller;

import com.webtech.urlshortener.service.UrlService;
import com.webtech.urlshortener.service.dto.ShortenUrlRequest;
import com.webtech.urlshortener.service.dto.ShortenedUrlTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ShortenedUrlTO shorten(@RequestBody ShortenUrlRequest shortenRequest) {
        return urlService.shorten(shortenRequest);
    }
}
