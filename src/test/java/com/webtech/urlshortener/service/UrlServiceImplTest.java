package com.webtech.urlshortener.service;

import com.webtech.urlshortener.repository.UrlRepository;
import com.webtech.urlshortener.service.dto.ShortenUrlRequest;
import com.webtech.urlshortener.service.dto.ShortenUrlResponse;
import com.webtech.urlshortener.service.dto.ShortenedUrlTO;
import com.webtech.urlshortener.service.dto.UserTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UrlServiceImplTest {

    public static final int USER_ID = 1;
    public static final int URLS_CREATED = 37;
    public static final int MAX_URLS = 57;
    public static final UserTO USER = new UserTO(USER_ID, "Name", "email", URLS_CREATED, MAX_URLS);
    public static final int URL_ID = 2;
    public static final String LONG_URL = "URL";
    public static final String SHORT_URL = "SHORT_URL";

    private final UrlRepository repo = Mockito.mock(UrlRepository.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private final RandomHashProvider hashProvider = Mockito.mock(RandomHashProvider.class);
    private final UrlServiceImpl service = new UrlServiceImpl(repo, userService, hashProvider);

    @Test
    public void testDelete() {
        service.delete(USER_ID, URL_ID);

        verify(repo).remove(USER_ID, URL_ID);
        verify(userService).urlRemoved(USER_ID);
    }

    @Test
    public void testShorten() {
        Mockito.when(userService.urlAdded(USER_ID))
                .thenReturn(USER);
        Mockito.when(hashProvider.getNextHash())
                .thenReturn(SHORT_URL);
        Mockito.when(repo.save(eq(USER_ID), any(ShortenedUrlTO.class)))
                .thenReturn(new ShortenedUrlTO(URL_ID, LONG_URL, SHORT_URL));

        ShortenUrlResponse result = service.shorten(USER_ID, new ShortenUrlRequest(LONG_URL));

        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(new ShortenUrlResponse(URL_ID, LONG_URL, SHORT_URL, URLS_CREATED, MAX_URLS));

        ArgumentCaptor<ShortenedUrlTO> captor = ArgumentCaptor.forClass(ShortenedUrlTO.class);
        verify(repo).save(eq(USER_ID), captor.capture());
        List<ShortenedUrlTO> allValues = captor.getAllValues();
        Assertions.assertThat(allValues).hasSize(1);
        ShortenedUrlTO passedArg = allValues.get(0);

        Assertions.assertThat(passedArg)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new ShortenedUrlTO(1, LONG_URL, SHORT_URL));

        verify(userService).urlAdded(USER_ID);

        verify(userService, never()).urlRemoved(USER_ID);
    }

    @Test
    public void testMockito() {
        Mockito.when(hashProvider.getNextHash())
                .thenReturn(SHORT_URL)
                .thenReturn(LONG_URL);

        System.out.println(hashProvider.getNextHash());
        System.out.println(hashProvider.getNextHash());
        System.out.println(hashProvider.getNextHash());
        System.out.println(hashProvider.getNextHash());
        System.out.println(hashProvider.getNextHash());
    }
}