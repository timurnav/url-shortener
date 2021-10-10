package com.webtech.urlshortener.service.url;

public interface UserUrlService {

    ShortenUrlResponse shorten(int userId, ShortenUrlRequest request);

    void delete(int userId, int urlId);
}
