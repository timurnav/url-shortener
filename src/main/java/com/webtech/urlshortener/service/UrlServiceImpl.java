package com.webtech.urlshortener.service;

import com.webtech.urlshortener.repository.UrlRepository;
import com.webtech.urlshortener.service.dto.ShortenUrlRequest;
import com.webtech.urlshortener.service.dto.ShortenUrlResponse;
import com.webtech.urlshortener.service.dto.ShortenedUrlTO;
import com.webtech.urlshortener.service.dto.UserTO;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UrlServiceImpl implements LongUrlProvider, UrlService {

    private final UrlRepository repository;
    private final UserService userService;

    public UrlServiceImpl(UrlRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public String getForShort(String shortUrl) {
        return repository.findByShortUrl(shortUrl);
    }

    @Override
    public ShortenUrlResponse shorten(int userId, ShortenUrlRequest shortenUrlRequest) {
        UserTO userTO = userService.urlAdded(userId);
        byte[] r = new byte[6];
        Random random = ThreadLocalRandom.current();
        random.nextBytes(r);
        String shortUrl = Base64.encodeBase64String(r);
        ShortenedUrlTO urlTO = repository.save(userId, shortenUrlRequest.longUrl, shortUrl);
        return ShortenUrlResponse.of(urlTO, userTO);
    }

    @Override
    public void delete(int userId, int urlId) {
        userService.urlRemoved(userId);
        repository.remove(userId, urlId);
    }
}
