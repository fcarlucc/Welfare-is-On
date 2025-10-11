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
                    "Service about gym around you, you can book a membership through our website",
                    "Gympass",
                    15L,
                    300.0,
                    20,
                    pillarService.findByName(PillarName.PHYSICAL),
                    "https://wellhub.com/it-it/"));


            servicesService.create(new Service(
                    "Check up Posturale, screening delle patologie muscolo-scheletriche e cefalalgiche odontoiatriche",
                    "Check up Posturale",
                    7L,
                    100.0,
                    15,
                    pillarService.findByName(PillarName.PHYSICAL),
                    12.462704,
                    41.918343,
                    "https://www.cliniqueops.com/valutazione-posturale.htm"));

            servicesService.create(new Service(
                    "Visita dermatologica e mappatura dei nevi",
                    "Dermatologia e nevi",
                    13L,
                    80.0,
                    20,
                    pillarService.findByName(PillarName.PHYSICAL),
                    14.062602,
                    40.819073,
                    "https://www.cupsolidale.it/prestazione/57d1785cd2bcc/"));

            servicesService.create(new Service(
                    "visita specialistica dermatologica in epiluminescenza",
                    "Epiluminescenza",
                    14L,
                    100.0,
                    15,
                    pillarService.findByName(PillarName.PHYSICAL),
                    8.901772,
                    45.738081,
                    "https://www.paginemediche.it/medicina-e-prevenzione/esami/epiluminescenza"));

            servicesService.create(new Service(
                    "Service about podcast",
                    "4books",
                    6L,
                    80.0,
                    20,
                    pillarService.findByName(PillarName.PSYCHOLOGICAL),
                    "https://4books.com/it"));

            servicesService.create(new Service(
                    "Service about music",
                    "Spotify",
                    11L,
                    60.0,
                    10,
                    pillarService.findByName(PillarName.PSYCHOLOGICAL),
                    "https://open.spotify.com/intl-it"));

            servicesService.create(new Service(
                    "servizio di supporto psicologico",
                    "Stimulus",
                    12L,
                    200.0,
                    20,
                    pillarService.findByName(PillarName.PSYCHOLOGICAL),
                    "https://stimulus-consulting.it/"));

            servicesService.create(new Service(
                    "Corsi di formazione per i manager su come riconoscere e gestire lo stress dei dipendenti.",
                    "Formazione per Manager",
                    18L,
                    200.0,
                    20,
                    pillarService.findByName(PillarName.PSYCHOLOGICAL),
                    "https://www.cegos.it/corsi-formazione/management-leadership"));

            servicesService.create(new Service(
                    "Service about convention",
                    "Convenzioni",
                    8L,
                    150.0,
                    20,
                    pillarService.findByName(PillarName.ECONOMIC),
                    "https://www.corporate-benefits.it/"));

            servicesService.create(new Service(
                    "Piattaforam per i flexible benefits",
                    "Leonardo one flex",
                    9L,
                    150.0,
                    20,
                    pillarService.findByName(PillarName.ECONOMIC),
                    "https://www.leonardowelfare.oneflex.aon.it"));

            servicesService.create(new Service(
                    "Servizio per la previdenza complementare",
                    "Previdenza complementare",
                    20L,
                    150.0,
                    20,
                    pillarService.findByName(PillarName.ECONOMIC),
                    "https://www.lavoro.gov.it/temi-e-priorita/previdenza/focus-on/previdenza-complementare/pagine/default#:~:text=La%20previdenza%20complementare%20%C3%A8%20basata,beneficiare%20di%20una%20pensione%20integrativa."));

            servicesService.create(new Service(
                    "Programmi di risparmio aziendali come fondi comuni o piani di accumulo.",
                    "Programmi di Risparmio",
                    19L,
                    150.0,
                    20,
                    pillarService.findByName(PillarName.ECONOMIC),
                    "https://www.fideuramdirect.it/it/investimenti/risparmiare-in-modo-programmato.html"));

            servicesService.create(new Service(
                    "Campo estivo ad H-Farm, il campo è destinato ai figli dei dipendenti Leonardo per lo svolgimento di fantastiche attività STEM",
                    "Leonardo summer camp",
                    10L,
                    500.0,
                    25,
                    pillarService.findByName(PillarName.FAMILY),
                    12.43765,
                    45.565466,
                    "https://www.h-farm.com/it"));

            servicesService.create(new Service(
                    "Assistenza e supporto per dipendenti che si occupano di familiari anziani o disabili.",
                    "Supporto ai Caregiver",
                    17L,
                    500.0,
                    25,
                    pillarService.findByName(PillarName.FAMILY),
                    "https://caregiver.regione.emilia-romagna.it/"));

            servicesService.create(new Service(
                    "Asilo nido aziendale o convenzioni con strutture locali. Servizio di babysitting flessibile per emergenze o orari di lavoro prolungati.",
                    "Servizi per l'Infanzia",
                    16L,
                    500.0,
                    25,
                    pillarService.findByName(PillarName.FAMILY),
                    "https://www.asgesa.it/servizi-per-linfanzia/"));
        };
    }
}
