package com.webtech.urlshortener.service;

import com.webtech.urlshortener.repository.ShortUrlRepository;
import com.webtech.urlshortener.repository.entity.ShortUrlEntity;
import com.webtech.urlshortener.repository.inmem.ShortUrlInMemRepository;
import com.webtech.urlshortener.service.url.RandomHashProvider;
import com.webtech.urlshortener.service.url.ShortenUrlRequest;
import com.webtech.urlshortener.service.url.ShortenUrlResponse;
import com.webtech.urlshortener.service.url.UserUrlServiceImpl;
import com.webtech.urlshortener.service.user.UserService;
import com.webtech.urlshortener.service.user.UserTO;
import com.webtech.urlshortener.service.user.UserUrlAdded;
import com.webtech.urlshortener.service.user.UserUrlRemoved;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserUrlServiceImplTest {

    public static final int USER_ID = 1;
    public static final int URLS_CREATED = 37;
    public static final int MAX_URLS = 57;
    public static final UserTO USER = new UserTO(USER_ID, "Name", "email", URLS_CREATED, MAX_URLS);
    public static final int URL_ID = 2;
    public static final String LONG_URL = "URL";
    public static final String SHORT_URL = "SHORT_URL";

    public static final ShortUrlEntity ENTITY =
            new ShortUrlEntity(URL_ID, LONG_URL, SHORT_URL, new Date(), USER_ID);

    private final ShortUrlRepository repo = Mockito.mock(ShortUrlInMemRepository.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private final RandomHashProvider hashProvider = Mockito.mock(RandomHashProvider.class);
    private final UserUrlServiceImpl service = new UserUrlServiceImpl(repo, userService, hashProvider);

    @AfterEach
    public void tearDown() {
        Mockito.reset(repo, userService, hashProvider);
    }

    @Test
    public void testDelete() {
        service.delete(USER_ID, URL_ID);

        verify(repo).remove(URL_ID);
        verify(userService).adjust(eq(USER_ID), any(UserUrlRemoved.class));
    }

    @Test
    public void testShorten() {
        Mockito.when(userService.adjust(eq(USER_ID), any(UserUrlAdded.class)))
                .thenReturn(USER);
        Mockito.when(hashProvider.getNextHash())
                .thenReturn(SHORT_URL);
        Mockito.when(repo.save(any(ShortUrlEntity.class)))
                .thenReturn(ENTITY);

        ShortenUrlResponse result = service.shorten(USER_ID, new ShortenUrlRequest(LONG_URL));

        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(new ShortenUrlResponse(URL_ID, LONG_URL, SHORT_URL, URLS_CREATED, MAX_URLS));

        ArgumentCaptor<ShortUrlEntity> captor = ArgumentCaptor.forClass(ShortUrlEntity.class);
        verify(repo).save(captor.capture());
        List<ShortUrlEntity> allValues = captor.getAllValues();
        Assertions.assertThat(allValues).hasSize(1);
        ShortUrlEntity passedArg = allValues.get(0);

        Assertions.assertThat(passedArg)
                .usingRecursiveComparison()
                .ignoringFields("id", "created")
                .isEqualTo(ENTITY);

        verify(userService).adjust(eq(USER_ID), any(UserUrlAdded.class));

        verify(userService, never()).adjust(eq(USER_ID), any(UserUrlRemoved.class));
    }
}