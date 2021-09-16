package com.webtech.urlshortener.service;

import com.webtech.urlshortener.service.dto.ShortenUrlRequest;
import com.webtech.urlshortener.service.dto.ShortenedUrlTO;

public interface UrlService {

    ShortenedUrlTO shorten(int userId, ShortenUrlRequest request);

    void delete(int userId, int urlId);
}
