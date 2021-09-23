package com.webtech.urlshortener.repository;

import com.webtech.urlshortener.service.dto.ShortenedUrlTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UrlRepository {

    // null object
    private static final ShortenedUrlTO MISSING_TO = new ShortenedUrlTO(-1, null, null);

    private final AtomicInteger counter = new AtomicInteger();
    private final Map<String, ShortenedUrlTO> urlsByShort = new HashMap<>();
    private final Map<Integer, ShortenedUrlTO> urlsById = new HashMap<>();

    public String findByShortUrl(String shortUrl) {
        return urlsByShort.getOrDefault(shortUrl, MISSING_TO).longUrl;
//        return Optional.ofNullable(urls.get(shortUrl))
//                .map(to -> to.longUrl)
//                .orElse(null);

//        ShortenedUrlTO to = urls.get(shortUrl);
//        if (to == null) {
//            return null;
//        }
//        return to.longUrl;
    }

    public ShortenedUrlTO save(int userId, ShortenedUrlTO toSave) {
        int id = counter.incrementAndGet();
        ShortenedUrlTO to = new ShortenedUrlTO(id, toSave.longUrl, toSave.shortUrl);
        urlsByShort.put(toSave.shortUrl, to);
        urlsById.put(id, to);
        return to;
    }

    public void remove(int userId, int urlId) {
        ShortenedUrlTO to = urlsById.get(urlId);
        if (to == null) {
            return;
        }
        urlsById.remove(urlId);
        urlsByShort.remove(to.shortUrl);
    }
}
