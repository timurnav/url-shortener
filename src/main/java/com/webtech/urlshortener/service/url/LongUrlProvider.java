package com.webtech.urlshortener.service.url;

public interface LongUrlProvider {

    String getForShort(String shortUrl);
}
