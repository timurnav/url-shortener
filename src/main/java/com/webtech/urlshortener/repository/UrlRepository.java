package com.webtech.urlshortener.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UrlRepository {

    private final Map<String, String> urls = new HashMap<>();

    public String findByShortUrl(String shortUrl) {
        return urls.get(shortUrl);
    }

    public void save(String longUrl, String shortUrl) {
        urls.put(shortUrl, longUrl);
    }
}
