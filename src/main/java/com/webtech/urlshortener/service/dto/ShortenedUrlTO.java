package com.webtech.urlshortener.service.dto;

public class ShortenedUrlTO {

    public final String longUrl;
    public final String shortUrl;

    public ShortenedUrlTO(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }
}
