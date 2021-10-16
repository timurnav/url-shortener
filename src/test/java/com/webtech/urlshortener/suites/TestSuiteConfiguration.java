package com.webtech.urlshortener.suites;

import com.webtech.urlshortener.runner.UrlShortenerApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(UrlShortenerApplication.class)
public class TestSuiteConfiguration {

}
