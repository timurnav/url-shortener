package com.webtech.urlshortener.service.url;

public class ShortenedUrlTO {

    public final int id;
    public final String longUrl;
    public final String shortUrl;

    public ShortenedUrlTO(int id, String longUrl, String shortUrl) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }
}
