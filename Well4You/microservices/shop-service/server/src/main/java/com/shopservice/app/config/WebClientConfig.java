package com.shopservice.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for creating a WebClient bean.
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates a WebClient bean used for making HTTP requests.
     *
     * @return The configured WebClient instance.
     */
    @Bean
    @Qualifier("WebClient")
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
