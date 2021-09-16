package com.webtech.urlshortener.service;

import com.webtech.urlshortener.service.dto.ShortenUrlRequest;
import com.webtech.urlshortener.service.dto.ShortenedUrlTO;

public interface UrlService {

    ShortenedUrlTO shorten(ShortenUrlRequest request);
}
