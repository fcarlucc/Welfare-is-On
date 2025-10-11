package com.coachservice.app.Initializer;

import com.coachservice.app.exception.DivisionNotFoundException;
import com.coachservice.app.exception.PillarNotFoundException;
import com.coachservice.app.model.Coach;
import com.coachservice.app.model.Division;
import com.coachservice.app.model.Pillar;
import com.coachservice.app.model.enumerator.DivisionName;
import com.coachservice.app.model.enumerator.PillarName;
import com.coachservice.app.service.CoachService;
import com.coachservice.app.service.DivisionService;
import com.coachservice.app.service.PillarService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PillarService pillarService;
    private final CoachService coachService;

    @Bean
    public CommandLineRunner initData(DivisionService divisionService) {
        return args -> {
            for (PillarName pillarName : PillarName.values()) {
                try {
                    pillarService.findByName(pillarName);
                } catch (PillarNotFoundException ignored) {
                    pillarService.save(new Pillar(pillarName));
                }
            }

            for (DivisionName divisionName : DivisionName.values()) {
                try {
                    divisionService.findByName(divisionName);
                } catch (DivisionNotFoundException ignored) {
                    divisionService.save(new Division(divisionName));
                }
            }

            Division aeroStructure = divisionService.findByName(DivisionName.AEROSTRUCTURE);
            Division electronic = divisionService.findByName(DivisionName.ELECTRONIC);
            Division cyberSecurity = divisionService.findByName(DivisionName.CYBERSECURITY);
            Division cloud = divisionService.findByName(DivisionName.CLOUD);

            Pillar family = pillarService.findByName(PillarName.FAMILY);
            Pillar economic = pillarService.findByName(PillarName.ECONOMIC);
            Pillar physical = pillarService.findByName(PillarName.PHYSICAL);
            Pillar psychological = pillarService.findByName(PillarName.PSYCHOLOGICAL);

            Coach coach = new Coach(9L, "Gioele", "Sabbatini", "GioeleSabbatini@rhyta.com", 2L, family, 15.4529, 41.502811, "03595715243", aeroStructure);
            coachService.create(coach);

            Coach coach2 = new Coach(10L, "Ugo", "Piccio", "UgoPiccio@jourrapide.com", 2L, physical, 12.482932, 41.89332, "03207098099", cyberSecurity);
            coachService.create(coach2);

            Coach coach3 = new Coach(11L, "Mauro", "Milani", "MauroMilani@teleworm.us", 2L, psychological, 11.2555762, 43.769871, "03207098098", cloud);
            coachService.create(coach3);

            Coach coach4 = new Coach(12L, "Giacomo", "Dellucci", "GiacomoDellucci@teleworm.us", 2L, economic, 9.189982, 45.464204, "03561055258", electronic);
            coachService.create(coach4);
        };
    }
}
