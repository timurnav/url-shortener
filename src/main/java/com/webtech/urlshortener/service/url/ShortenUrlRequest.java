package com.webtech.urlshortener.service.url;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ShortenUrlRequest {

    public final String longUrl;

    @JsonCreator
    public ShortenUrlRequest(String longUrl) {
        this.longUrl = longUrl;
    }
}
