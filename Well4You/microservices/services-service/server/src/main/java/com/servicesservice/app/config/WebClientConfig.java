package com.servicesservice.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for WebClient bean instantiation.
 */
@Configuration
public class WebClientConfig {

    /**
     * Bean definition for creating a WebClient instance.
     *
     * @return WebClient instance configured with default settings.
     */
    @Bean
    @Qualifier("WebClient")
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
