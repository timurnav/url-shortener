package com.webtech.urlshortener.repository;

import com.webtech.urlshortener.repository.entity.ShortUrlEntity;

public interface ShortUrlRepository {
    String findByShortUrl(String shortUrl);

    ShortUrlEntity save(ShortUrlEntity toSave);

    void remove(int urlId);
}
