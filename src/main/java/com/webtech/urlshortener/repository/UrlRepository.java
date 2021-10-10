package com.webtech.urlshortener.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UrlRepository {

    private final AtomicInteger counter = new AtomicInteger();
    private final Map<String, ShortenedUrlEntity> urlsByShort = new HashMap<>();
    private final Map<Integer, ShortenedUrlEntity> urlsById = new HashMap<>();

    public String findByShortUrl(String shortUrl) {
        ShortenedUrlEntity entity = urlsByShort.get(shortUrl);
        if (entity == null) {
            return null;
        }
        return entity.getLongUrl();
    }

    public ShortenedUrlEntity save(ShortenedUrlEntity toSave) {
        int id = counter.incrementAndGet();
        toSave.setId(id);
        urlsByShort.put(toSave.getShortUrl(), toSave);
        urlsById.put(id, toSave);
        return toSave;
    }

    public void remove(int urlId) {
        ShortenedUrlEntity to = urlsById.get(urlId);
        if (to == null) {
            return;
        }
        urlsById.remove(urlId);
        urlsByShort.remove(to.getShortUrl());
    }
}
