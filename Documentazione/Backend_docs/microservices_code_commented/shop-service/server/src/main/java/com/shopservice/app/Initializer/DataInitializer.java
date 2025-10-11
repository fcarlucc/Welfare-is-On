package com.shopservice.app.Initializer;

import com.shopservice.app.model.Purchase;
import com.shopservice.app.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PurchaseService purchaseService;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            purchaseService.create(new Purchase(1L, 1L));
            purchaseService.create(new Purchase(1L, 2L));
            purchaseService.create(new Purchase(1L, 4L));
            purchaseService.create(new Purchase(2L, 3L));
            purchaseService.create(new Purchase(3L, 6L));
            purchaseService.create(new Purchase(3L, 7L));
            purchaseService.create(new Purchase(3L, 8L));
            purchaseService.create(new Purchase(3L, 9L));
            purchaseService.create(new Purchase(3L, 10L));
            purchaseService.create(new Purchase(4L, 11L));
            purchaseService.create(new Purchase(4L, 12L));
            purchaseService.create(new Purchase(4L, 13L));
            purchaseService.create(new Purchase(5L, 14L));
            purchaseService.create(new Purchase(6L, 1L));
            purchaseService.create(new Purchase(7L, 1L));
            purchaseService.create(new Purchase(7L, 2L));
            purchaseService.create(new Purchase(7L, 3L));
            purchaseService.create(new Purchase(1L, 12L));
            purchaseService.create(new Purchase(2L, 12L));
            purchaseService.create(new Purchase(2L, 13L));
            purchaseService.create(new Purchase(2L, 14L));
        };
    }
}
