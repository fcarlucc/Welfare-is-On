package com.apigateway.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /**
     * Configures a WebClient bean with the qualifier "WebClient".
     *
     * @return WebClient instance configured with default settings.
     */
    @Bean
    @Qualifier("WebClient")
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
