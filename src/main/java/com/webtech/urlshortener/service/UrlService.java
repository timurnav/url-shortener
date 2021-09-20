package com.webtech.urlshortener.service;

import com.webtech.urlshortener.service.dto.ShortenUrlRequest;
import com.webtech.urlshortener.service.dto.ShortenUrlResponse;

public interface UrlService {

    ShortenUrlResponse shorten(int userId, ShortenUrlRequest request);

    void delete(int userId, int urlId);
}
