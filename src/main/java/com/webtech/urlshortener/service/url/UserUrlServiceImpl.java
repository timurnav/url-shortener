package com.webtech.urlshortener.service.url;

import com.webtech.urlshortener.repository.ShortenedUrlEntity;
import com.webtech.urlshortener.repository.UrlRepository;
import com.webtech.urlshortener.service.user.UserService;
import com.webtech.urlshortener.service.user.UserTO;
import com.webtech.urlshortener.service.user.UserUrlAdded;
import com.webtech.urlshortener.service.user.UserUrlRemoved;

import java.time.Instant;

public class UserUrlServiceImpl implements LongUrlProvider, UserUrlService {

    private final UrlRepository repository;
    private final UserService userService;
    private final RandomHashProvider hashProvider;

    public UserUrlServiceImpl(UrlRepository repository,
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
        UserTO userTO = userService.adjust(userId, new UserUrlAdded());

        ShortenedUrlEntity toSave = new ShortenedUrlEntity();
        toSave.setLongUrl(shortenUrlRequest.longUrl);
        toSave.setOwnerId(userTO.id);
        toSave.setShortUrl(hashProvider.getNextHash());
        toSave.setCreated(Instant.now());

        ShortenedUrlEntity saved = repository.save(toSave);

        return new ShortenUrlResponse(saved.getId(), saved.getLongUrl(),
                saved.getShortUrl(), userTO.urlsCreated, userTO.maxUrls);
    }

    @Override
    public void delete(int userId, int urlId) {
        userService.adjust(userId, new UserUrlRemoved());
        repository.remove(urlId);
    }
}
