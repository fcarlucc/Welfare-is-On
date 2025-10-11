package com.scheduleservice.app.service;

import com.scheduleservice.app.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Service for sending emails via an external notification service.
 * Uses {@link WebClient} to make HTTP requests to the email service.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final WebClient webClient;

    @Value("${notification.service.name}")
    private String emailServiceIp;

    @Value("${notification.service.port}")
    private String emailServicePort;

    /**
     * Sends an email using the external email notification service.
     *
     * @param email   the recipient's email address
     * @param text    the content of the email
     * @param subject the subject of the email
     * @return {@code true} if the email was sent successfully, {@code false} otherwise
     */
    public Boolean sendEmail(String email, String text, String subject) {
        System.out.println("\n" + email + "\n");
        return webClient.post()
                .uri("http://" + emailServiceIp + ":" + emailServicePort + "/email/general")
                .bodyValue(new EmailDto(email, text, subject))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
}
