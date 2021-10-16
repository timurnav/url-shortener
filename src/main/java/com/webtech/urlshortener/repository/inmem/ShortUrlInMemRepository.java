package com.webtech.urlshortener.repository.inmem;

import com.webtech.urlshortener.configuration.Resettable;
import com.webtech.urlshortener.repository.ShortUrlRepository;
import com.webtech.urlshortener.repository.entity.ShortUrlEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ShortUrlInMemRepository implements ShortUrlRepository, Resettable {

    private final AtomicInteger counter = new AtomicInteger();
    private final Map<String, ShortUrlEntity> urlsByShort = new HashMap<>();
    private final Map<Integer, ShortUrlEntity> urlsById = new HashMap<>();

    @Override
    public String findByShortUrl(String shortUrl) {
        ShortUrlEntity entity = urlsByShort.get(shortUrl);
        if (entity == null) {
            return null;
        }
        return entity.getLongUrl();
    }

    @Override
    public ShortUrlEntity save(ShortUrlEntity toSave) {
        int id = counter.incrementAndGet();
        toSave.setId(id);
        urlsByShort.put(toSave.getShortUrl(), toSave);
        urlsById.put(id, toSave);
        return toSave;
    }

    @Override
    public void remove(int urlId) {
        ShortUrlEntity to = urlsById.get(urlId);
        if (to == null) {
            return;
        }
        urlsById.remove(urlId);
        urlsByShort.remove(to.getShortUrl());
    }

    @Override
    public void reset() {
        counter.set(0);
        urlsByShort.clear();
        urlsById.clear();
    }
}
