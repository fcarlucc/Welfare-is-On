package com.apigateway.app.Initializer;

import com.apigateway.app.model.*;
import com.apigateway.app.model.enumerator.RoleName;
import com.apigateway.app.service.*;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final RoleService roleService;

    @Bean
    public CommandLineRunner initData(UserService userService) {
        return args -> {
            for (RoleName roleName : RoleName.values()) {
                if (roleService.findByName(roleName).isEmpty()) {
                    roleService.save(new Role(roleName));
                }
            }

            Role userRole = roleService.findByName(RoleName.ROLE_USER).get();
            Set<Role> rolesUser = new HashSet<>();
            rolesUser.add(userRole);

            Role adminRole = roleService.findByName(RoleName.ROLE_ADMIN).get();
            Set<Role> rolesAdmin = new HashSet<>();
            rolesAdmin.add(adminRole);

            Role coachRole = roleService.findByName(RoleName.ROLE_COACH).get();
            Set<Role> rolesCoach = new HashSet<>();
            rolesCoach.add(coachRole);

            User user1 = new User("Manuele", "Longo", "manuelelng03@gmail.com", "manuelelng03@gmail.com1A", rolesUser, false, true, false);
            userService.create(user1);

            User user2 = new User("Flaviano", "Carlucci", "flaviano@gmail.com", "flaviano@gmail.com1A", rolesUser, false, true, false);
            userService.create(user2);

            User user3 = new User("Lorenzo", "Nicotera", "lorenzo@gmail.com", "lorenzo@gmail.com1A", rolesUser, false, true, false);
            userService.create(user3);

            User user4 = new User("Alessio", "Buonomo", "alessio@gmail.com", "alessio@gmail.com1A", rolesUser, false, true, false);
            userService.create(user4);

            User user5 = new User("Giulia", "Corradi", "giulia@gmail.com", "giulia@gmail.com1A", rolesUser, false, true, false);
            userService.create(user5);

            User user6 = new User("Federica", "Pellegrini", "federica@gmail.com", "federica@gmail.com1A", rolesUser, false, true, false);
            userService.create(user6);

            User user7 = new User("francesca", "apice", "francesca@gmail.com", "francesca@gmail.com1A", rolesUser, false, true, false);
            userService.create(user7);

            User admin = new User("admin", "admin", adminEmail, adminPassword, rolesAdmin, false, true, false);
            userService.create(admin);

            User coach = new User("Gioele", "Sabbatini", "GioeleSabbatini@rhyta.com", "GioeleSabbatini@rhyta.com1A", rolesCoach, false, true, false);
            userService.create(coach);

            User coach2 = new User("Francesca", "Piccio", "FrancescaPiccio@jourrapide.com", "FrancescaPiccio@jourrapide.com1A", rolesCoach, false, true, false);
            userService.create(coach2);

            User coach3 = new User("Laura", "Milani", "LauraMilani@teleworm.us", "LauraMilani@teleworm.usm1A", rolesCoach, false, true, false);
            userService.create(coach3);

            User coach4 = new User("Giulia", "Dellucci", "GiuliaDellucci@teleworm.us", "GiuliaDellucci@teleworm.us1A", rolesCoach, false, true, false);
            userService.create(coach4);
        };
    }
}
