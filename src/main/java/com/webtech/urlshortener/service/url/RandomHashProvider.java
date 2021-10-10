package com.webtech.urlshortener.service.url;

import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomHashProvider {

    public String getNextHash() {
        byte[] r = new byte[6];
        Random random = ThreadLocalRandom.current();
        random.nextBytes(r);
        return Base64.encodeBase64String(r);
    }
}
