package com.webtech.urlshortener.service.dto;

public class ShortenUrlResponse {

    public final int id;
    public final String longUrl;
    public final String shortUrl;
    public final int urlsCreated;
    public final int maxUrls;

    public ShortenUrlResponse(int id, String longUrl, String shortUrl, int urlsCreated, int maxUrls) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.urlsCreated = urlsCreated;
        this.maxUrls = maxUrls;
    }

    public static ShortenUrlResponse of(ShortenedUrlTO urlTO, UserTO userTO) {
        return new ShortenUrlResponse(urlTO.id, urlTO.longUrl, urlTO.shortUrl, userTO.urlsCreated, userTO.maxUrls);
    }
}
