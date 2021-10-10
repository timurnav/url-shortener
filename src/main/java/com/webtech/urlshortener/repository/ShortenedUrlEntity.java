package com.webtech.urlshortener.repository;

import java.time.Instant;

public class ShortenedUrlEntity {

    private int id;
    private String longUrl;
    private String shortUrl;
    private Instant created;
    private int ownerId;

    public ShortenedUrlEntity() {
    }

    public ShortenedUrlEntity(int id, String longUrl, String shortUrl, Instant created, int ownerId) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.created = created;
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
