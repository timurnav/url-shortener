package com.webtech.urlshortener.suites;

import com.webtech.urlshortener.repository.entity.UserEntity;
import com.webtech.urlshortener.service.url.ShortenUrlRequest;
import com.webtech.urlshortener.service.url.ShortenUrlResponse;
import com.webtech.urlshortener.web.ResponseError;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UrlShorteningTestSuite extends BaseTestSuite {

    private int shortUrlId;
    private int userId;
    private String shortUrl;

    @Test
    public void shortenUrlTest() throws Exception {
        UserEntity user = createUser(builder -> {
            builder.setUrlsCreated(0);
            builder.setMaxUrls(1);
        });
        this.userId = user.getId();

        ShortenUrlRequest request = new ShortenUrlRequest("https://google.com");
        MvcResult result = mockMvc.perform(put("/users/{userId}/urls", userId).content(asJson(request))
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ShortenUrlResponse response = fromResponse(result, ShortenUrlResponse.class);
        this.shortUrlId = response.id;
        this.shortUrl = response.shortUrl;
        assertThat(response.longUrl).isEqualTo(request.longUrl);
        assertThat(response.urlsCreated).isEqualTo(1);
        assertThat(response.maxUrls).isEqualTo(1);

        UserEntity afterShortening = getUser(userId);
        assertThat(afterShortening.getUrlsCreated()).isEqualTo(1);
        assertThat(afterShortening.getMaxUrls()).isEqualTo(1);

        mockMvc.perform(get("/to/{shortUrl}", shortUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(request.longUrl));
    }

    @Test
    public void deleteUrlTest() throws Exception {
        shortenUrlTest();

        mockMvc.perform(delete("/users/{userId}/urls/{urlId}", userId, shortUrlId))
                .andExpect(status().isOk());

        UserEntity deleting = getUser(userId);
        assertThat(deleting.getUrlsCreated()).isEqualTo(0);
        assertThat(deleting.getMaxUrls()).isEqualTo(1);

        mockMvc.perform(get("/to/{shortUrl}", shortUrl))
                .andExpect(redirectedUrl("/not_found.html"));
    }

    @Test
    public void urlsNumberExceededTest() throws Exception {
        UserEntity user = createUser(builder -> {
            builder.setUrlsCreated(1);
            builder.setMaxUrls(1);
        });

        ShortenUrlRequest request = new ShortenUrlRequest("https://google.com");
        MvcResult result = mockMvc.perform(put("/users/{userId}/urls", user.getId()).content(asJson(request))
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andReturn();
        ResponseError responseError = fromResponse(result, ResponseError.class);
        Assertions.assertThat(responseError.errorMessage)
                .isEqualTo("User " + user.getId() + " with roles [] urls number exceeded: 1/1");
    }
}
