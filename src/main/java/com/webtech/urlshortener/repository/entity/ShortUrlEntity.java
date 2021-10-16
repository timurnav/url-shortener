package com.webtech.urlshortener.repository.entity;

import java.util.Date;

public class ShortUrlEntity {

    private int id;
    private String longUrl;
    private String shortUrl;
    private Date created;
    private int ownerId;

    public ShortUrlEntity() {
    }

    public ShortUrlEntity(int id, String longUrl, String shortUrl, Date created, int ownerId) {
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
