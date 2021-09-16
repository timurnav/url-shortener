package com.webtech.urlshortener.service;

public interface LongUrlProvider {

    String getForShort(String shortUrl);
}
