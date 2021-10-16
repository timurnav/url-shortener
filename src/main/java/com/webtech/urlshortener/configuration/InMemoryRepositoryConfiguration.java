package com.webtech.urlshortener.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("in-memory")
@ComponentScan("com.webtech.urlshortener.repository.inmem")
public class InMemoryRepositoryConfiguration {
}
