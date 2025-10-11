package com.servicesservice.app.Initializer;

import com.servicesservice.app.exception.PillarNotFoundException;
import com.servicesservice.app.model.Pillar;
import com.servicesservice.app.model.Service;
import com.servicesservice.app.model.enumerator.PillarName;
import com.servicesservice.app.service.PillarService;
import com.servicesservice.app.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PillarService pillarService;
    private final ServicesService servicesService;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            for (PillarName pillarName : PillarName.values()) {
                try {
                    pillarService.findByName(pillarName);
                } catch (PillarNotFoundException ignored) {
                    pillarService.save(new Pillar(pillarName));
                }
            }

            servicesService.create(new Service(
                    "a service about gym",
                    "gympass",
                    1L,
                    1200.0,
                    20,
                    pillarService.findByName(PillarName.PHYSICAL),
                    12.487035,
                    41.899626,
                    "https://wellhub.com/it-it/"));


            servicesService.create(new Service(
                    "a service about travel deals",
                    "travelpass",
                    1L,
                    3000.0,
                    15,
                    pillarService.findByName(PillarName.FAMILY),
                    "https://travelhub.com/it-it/"));


            servicesService.create(new Service(
                    "a service about book clubs",
                    "bookpass",
                    1L,
                    500.0,
                    50,
                    pillarService.findByName(PillarName.PSYCHOLOGICAL),
                    "https://readhub.com/it-it/"));


            servicesService.create(new Service(
                    "a service about financial planning",
                    "financepass",
                    1L,
                    1500.0,
                    10,
                    pillarService.findByName(PillarName.ECONOMIC),
                    12.493389,
                    41.897900,
                    "https://financehub.com/it-it/"));

            servicesService.create(new Service(
                    "a service about cooking classes",
                    "cookpass",
                    1L,
                    800.0,
                    25,
                    pillarService.findByName(PillarName.FAMILY),
                    12.490829,
                    41.894650,
                    "https://foodhub.com/it-it/"));

            servicesService.create(new Service(
                    "a service about tech workshops",
                    "techpass",
                    1L,
                    2000.0,
                    30,
                    pillarService.findByName(PillarName.PSYCHOLOGICAL),
                    "https://techhub.com/it-it/"));

            servicesService.create(new Service(
                    "a service about podcast",
                    "4books",
                    1L,
                    1200.0,
                    20,
                    pillarService.findByName(PillarName.PSYCHOLOGICAL),
                    "https://4books.com/it"));

            servicesService.create(new Service(
                    "a service about music",
                    "spotify",
                    1L,
                    1200.0,
                    20,
                    pillarService.findByName(PillarName.PSYCHOLOGICAL),
                    "https://open.spotify.com/intl-it"));

            servicesService.create(new Service(
                    "a service about convention",
                    "convention",
                    1L,
                    1200.0,
                    20,
                    pillarService.findByName(PillarName.ECONOMIC),
                    "https://www.corporate-benefits.it/"));

            servicesService.create(new Service(
                    "a service about gym",
                    "gympass",
                    1L,
                    1200.0,
                    20,
                    pillarService.findByName(PillarName.PSYCHOLOGICAL),
                    12.487035,
                    41.899626,
                    "https://wellhub.com/it-it/"));

            servicesService.create(new Service(
                    "a service about travel deals",
                    "travelpass",
                    1L,
                    3000.0,
                    15,
                    pillarService.findByName(PillarName.ECONOMIC),
                    "https://travelhub.com/it-it/"));

            servicesService.create(new Service(
                    "a service about book clubs",
                    "bookpass",
                    1L,
                    500.0,
                    50,
                    pillarService.findByName(PillarName.PHYSICAL),
                    "https://readhub.com/it-it/"));

            servicesService.create(new Service(
                    "a service about financial planning",
                    "financepass",
                    1L,
                    1500.0,
                    10,
                    pillarService.findByName(PillarName.PHYSICAL),
                    12.493389,
                    41.897900,
                    "https://financehub.com/it-it/"));

            servicesService.create(new Service(
                    "a service about cooking classes",
                    "cookpass",
                    1L,
                    800.0,
                    25,
                    pillarService.findByName(PillarName.ECONOMIC),
                    "https://foodhub.com/it-it/"));

            servicesService.create(new Service(
                    "a service about cooking classes",
                    "cookpass",
                    1L,
                    800.0,
                    25,
                    pillarService.findByName(PillarName.PHYSICAL),
                    "https://foodhub.com/it-it/"));
        };
    }
}
