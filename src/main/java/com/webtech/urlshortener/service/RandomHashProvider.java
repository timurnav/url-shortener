package com.webtech.urlshortener.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RandomHashProvider {

    public String getNextHash() {
        byte[] r = new byte[6];
        Random random = ThreadLocalRandom.current();
        random.nextBytes(r);
        return Base64.encodeBase64String(r);
    }
}
