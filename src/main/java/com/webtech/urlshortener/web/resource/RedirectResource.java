package com.webtech.urlshortener.web.resource;

import com.webtech.urlshortener.service.LongUrlProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RedirectResource {

    private final LongUrlProvider urlProvider;

    public RedirectResource(LongUrlProvider urlProvider) {
        this.urlProvider = urlProvider;
    }

    @GetMapping("to/{shortUrl}")
    public String processForm(@PathVariable String shortUrl) {
        String longUrl = urlProvider.getForShort(shortUrl);
        if (longUrl == null) {
            return "/not_found.html";
        }
        return "redirect:" + longUrl;
    }


}
