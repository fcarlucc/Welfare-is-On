package com.scheduleservice.app.Initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    @Bean
    public CommandLineRunner initData() {
        return args -> {
        };
    }
}
