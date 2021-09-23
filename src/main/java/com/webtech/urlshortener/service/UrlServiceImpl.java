package com.webtech.urlshortener.service;

import com.webtech.urlshortener.repository.UrlRepository;
import com.webtech.urlshortener.service.dto.ShortenUrlRequest;
import com.webtech.urlshortener.service.dto.ShortenUrlResponse;
import com.webtech.urlshortener.service.dto.ShortenedUrlTO;
import com.webtech.urlshortener.service.dto.UserTO;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements LongUrlProvider, UrlService {

    private final UrlRepository repository;
    private final UserService userService;
    private final RandomHashProvider hashProvider;

    public UrlServiceImpl(UrlRepository repository,
                          UserService userService,
                          RandomHashProvider hashProvider) {
        this.repository = repository;
        this.userService = userService;
        this.hashProvider = hashProvider;
    }

    @Override
    public String getForShort(String shortUrl) {
        return repository.findByShortUrl(shortUrl);
    }

    @Override
    public ShortenUrlResponse shorten(int userId, ShortenUrlRequest shortenUrlRequest) {
        UserTO userTO = userService.urlAdded(userId);
        String shortUrl = hashProvider.getNextHash();
        ShortenedUrlTO urlTO = repository.save(userId, new ShortenedUrlTO(-1, shortenUrlRequest.longUrl, shortUrl));
        return ShortenUrlResponse.of(urlTO, userTO);
    }

    @Override
    public void delete(int userId, int urlId) {
        userService.urlRemoved(userId);
        repository.remove(userId, urlId);
    }
}
