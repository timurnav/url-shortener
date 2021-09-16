package com.webtech.urlshortener.service;

import com.webtech.urlshortener.repository.UrlRepository;
import com.webtech.urlshortener.service.dto.ShortenUrlRequest;
import com.webtech.urlshortener.service.dto.ShortenedUrlTO;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UrlServiceImpl implements LongUrlProvider, UrlService {

    private final UrlRepository repository;

    public UrlServiceImpl(UrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getForShort(String shortUrl) {
        return repository.findByShortUrl(shortUrl);
    }

    @Override
    public ShortenedUrlTO shorten(int userId, ShortenUrlRequest shortenUrlRequest) {
        byte[] r = new byte[6];
        Random random = ThreadLocalRandom.current();
        random.nextBytes(r);
        String shortUrl = Base64.encodeBase64String(r);
        return repository.save(userId, shortenUrlRequest.longUrl, shortUrl);
    }

    @Override
    public void delete(int userId, int urlId) {
        repository.remove(userId, urlId);
    }
}
