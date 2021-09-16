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

    private final UrlRepository dao;

    public UrlServiceImpl(UrlRepository dao) {
        this.dao = dao;
    }

    @Override
    public String getForShort(String shortUrl) {
        return dao.findByShortUrl(shortUrl);
    }

    @Override
    public ShortenedUrlTO shorten(ShortenUrlRequest shortenUrlRequest) {
        byte[] r = new byte[6];
        Random random = ThreadLocalRandom.current();
        random.nextBytes(r);
        String shortUrl = Base64.encodeBase64String(r);
        dao.save(shortenUrlRequest.longUrl, shortUrl);
        return new ShortenedUrlTO(shortenUrlRequest.longUrl, shortUrl);
    }
}
