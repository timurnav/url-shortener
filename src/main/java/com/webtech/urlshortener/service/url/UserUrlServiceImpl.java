package com.webtech.urlshortener.service.url;

import com.webtech.urlshortener.repository.ShortUrlRepository;
import com.webtech.urlshortener.repository.entity.ShortUrlEntity;
import com.webtech.urlshortener.service.user.UserService;
import com.webtech.urlshortener.service.user.UserTO;
import com.webtech.urlshortener.service.user.UserUrlAdded;
import com.webtech.urlshortener.service.user.UserUrlRemoved;

import java.util.Date;

public class UserUrlServiceImpl implements LongUrlProvider, UserUrlService {

    private final ShortUrlRepository repository;
    private final UserService userService;
    private final RandomHashProvider hashProvider;

    public UserUrlServiceImpl(ShortUrlRepository repository,
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

        ShortUrlEntity toSave = new ShortUrlEntity();
        toSave.setLongUrl(shortenUrlRequest.longUrl);
        toSave.setOwnerId(userTO.id);
        toSave.setShortUrl(hashProvider.getNextHash());
        toSave.setCreated(new Date());

        ShortUrlEntity saved = repository.save(toSave);

        return new ShortenUrlResponse(saved.getId(), saved.getLongUrl(),
                saved.getShortUrl(), userTO.urlsCreated, userTO.maxUrls);
    }

    @Override
    public void delete(int userId, int urlId) {
        userService.adjust(userId, new UserUrlRemoved());
        repository.remove(urlId);
    }
}
