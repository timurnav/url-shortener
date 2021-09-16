package com.webtech.urlshortener.web.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RedirectResource {

    @GetMapping("to/{shortUrl}")
    public String processForm(@PathVariable String shortUrl) {
        return "redirect:http://www.yahoo.com";
    }


}
