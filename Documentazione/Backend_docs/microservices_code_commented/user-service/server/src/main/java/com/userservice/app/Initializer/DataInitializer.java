package com.userservice.app.Initializer;

import com.userservice.app.model.*;
import com.userservice.app.model.enumerator.InterestName;
import com.userservice.app.model.enumerator.MaritalStatusName;
import com.userservice.app.model.enumerator.TitleName;
import com.userservice.app.service.InterestService;
import com.userservice.app.service.MaritalStatusService;
import com.userservice.app.service.TitleService;
import com.userservice.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final InterestService interestService;
    private final MaritalStatusService maritalStatusService;
    private final TitleService titleService;
    private final UserService userService;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            for (InterestName interestName : InterestName.values()) {
                if (interestService.findByName(interestName).isEmpty()) {
                    interestService.save(new Interest(interestName));
                }
            }
            for (MaritalStatusName maritalStatusName : MaritalStatusName.values()) {
                if (maritalStatusService.findByName(maritalStatusName).isEmpty()) {
                    maritalStatusService.save(new MaritalStatus(maritalStatusName));
                }
            }
            for (TitleName titleName : TitleName.values()) {
                if (titleService.findByName(titleName).isEmpty()) {
                    titleService.save(new Title(titleName));
                }
            }

            MaritalStatus maritalStatusSingle = maritalStatusService.findByName(MaritalStatusName.SINGLE).get();
            MaritalStatus maritalStatusMarried = maritalStatusService.findByName(MaritalStatusName.MARRIED).get();
            Title titleSignor = titleService.findByName(TitleName.SIGNOR).get();
            Title titleSignora = titleService.findByName(TitleName.SIGNORA).get();

            Interest sport = interestService.findByName(InterestName.SPORT).get();
            Interest reading = interestService.findByName(InterestName.READING).get();
            Interest viaggi = interestService.findByName(InterestName.TRAVEL).get();
            Interest leisure = interestService.findByName(InterestName.LEISURE).get();
            Interest prevention = interestService.findByName(InterestName.PREVENTION).get();
            Set<Interest> interestsUser = new HashSet<>();
            interestsUser.add(sport);
            interestsUser.add(reading);
            interestsUser.add(viaggi);
            interestsUser.add(leisure);
            interestsUser.add(prevention);

            LocalDate localDate = LocalDate.now();
            Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);

            User user1 = new User(1L, "Manuele", "Longo", "manuelelng03@gmail.com", 12.502527, 41.900582, date);
            UserSurvey userSurvey1 = new UserSurvey(maritalStatusSingle, titleSignor, false, false, interestsUser);
            user1.setUserSurvey(userSurvey1);
            userSurvey1.setUser(user1);
            userService.create(user1);

            User user2 = new User(2L, "Flaviano", "Carlucci", "flaviano@gmail.com",0.0, 0.0, date);
            UserSurvey userSurvey2 = new UserSurvey(maritalStatusSingle, titleSignor, false, false, interestsUser);
            user2.setUserSurvey(userSurvey2);
            userSurvey2.setUser(user2);
            userService.create(user2);

            User user3 = new User(3L, "Lorenzo", "Nicotera", "lorenzo@gmail.com", 0.0, 0.0, date);
            UserSurvey userSurvey3 = new UserSurvey(maritalStatusSingle, titleSignor, false, false, interestsUser);
            user3.setUserSurvey(userSurvey3);
            userSurvey3.setUser(user3);
            userService.create(user3);

            User user4 = new User(4L, "Alessio", "Buonomo", "alessio@gmail.com", 0.0, 0.0, date);
            UserSurvey userSurvey4 = new UserSurvey(maritalStatusSingle, titleSignor, false, false, interestsUser);
            user4.setUserSurvey(userSurvey4);
            userSurvey4.setUser(user4);
            userService.create(user4);

            User user5 = new User(5L, "Giulia", "Corradi", "giulia@gmail.com", 0.0, 0.0, date);
            UserSurvey userSurvey5 = new UserSurvey(maritalStatusMarried, titleSignora, true, true, interestsUser);
            user5.setUserSurvey(userSurvey5);
            userSurvey5.setUser(user5);
            userService.create(user5);

            User user6 = new User(6L, "Federica", "Pellegrini", "federica@gmail.com", 0.0, 0.0, date);
            UserSurvey userSurvey6 = new UserSurvey(maritalStatusSingle, titleSignora, true, false, interestsUser);
            user6.setUserSurvey(userSurvey6);
            userSurvey6.setUser(user6);
            userService.create(user6);

            User user7 = new User(7L, "francesca", "apice", "francesca@gmail.com", 0.0, 0.0, date);
            UserSurvey userSurvey7 = new UserSurvey(maritalStatusMarried, titleSignora, false, true, interestsUser);
            user7.setUserSurvey(userSurvey7);
            userSurvey7.setUser(user7);
            userService.create(user7);
        };
    }
}
