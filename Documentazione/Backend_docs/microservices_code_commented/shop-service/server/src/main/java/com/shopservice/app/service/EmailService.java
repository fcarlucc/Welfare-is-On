package com.shopservice.app.service;

import com.shopservice.app.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Service class responsible for sending emails using a web client.
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
     * Sends an email using the configured email service.
     *
     * @param email   The recipient's email address.
     * @param text    The content of the email.
     * @param subject The subject of the email.
     * @return true if the email was successfully sent; false otherwise.
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
