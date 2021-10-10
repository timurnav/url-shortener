package com.webtech.urlshortener.service;

import com.webtech.urlshortener.repository.PurchaseRepository;
import com.webtech.urlshortener.repository.UrlRepository;
import com.webtech.urlshortener.repository.UserRepository;
import com.webtech.urlshortener.service.purchase.PurchaseService;
import com.webtech.urlshortener.service.purchase.UserPurchaseService;
import com.webtech.urlshortener.service.url.RandomHashProvider;
import com.webtech.urlshortener.service.url.UserUrlServiceImpl;
import com.webtech.urlshortener.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusinessConfiguration {

    @Bean
    public UserPurchaseService userPurchaseService(UserService userService,
                                                   PurchaseService purchaseService) {
        return new UserPurchaseService(userService, purchaseService);
    }

    @Bean
    public PurchaseService purchaseService(PurchaseRepository repository) {
        return new PurchaseService(repository);
    }

    @Bean
    public UserUrlServiceImpl userUrlService(UserService userService,
                                             UrlRepository urlRepository,
                                             RandomHashProvider hashProvider) {
        return new UserUrlServiceImpl(urlRepository, userService, hashProvider);
    }

    @Bean
    public UserService userService(UserRepository userRepository,
                                   @Value("${url-shortener.users.max-urls-default:1000}") int maxUrlsDefault) {
        return new UserService(userRepository, maxUrlsDefault);
    }

    @Bean
    public RandomHashProvider hashProvider() {
        return new RandomHashProvider();
    }
}
