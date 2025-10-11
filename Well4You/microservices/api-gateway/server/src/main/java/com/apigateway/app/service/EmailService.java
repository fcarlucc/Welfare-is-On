package com.apigateway.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Service class handling email-related operations using WebClient.
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
     * Sends OTP (One-Time Password) via email service.
     * @param email String representing the recipient's email address.
     * @param otp String containing the OTP to send.
     * @param endpoint String representing the endpoint for OTP verification.
     * @return true if email sending is successful, false otherwise.
     */
    public Boolean sendEmailOtp(String email, String otp, String endpoint) {
        System.out.println("\n\n\n" + email + ", " + otp + ", " + endpoint + "\n\n\n");
        return webClient.post()
                .uri("http://" + emailServiceIp + ":" + emailServicePort + "/email" + endpoint,
                        uriBuilder -> uriBuilder.queryParam("email", email).queryParam("otp", otp).build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    /**
     * Sends a general email through email service.
     * @param email String representing the recipient's email address.
     * @param endpoint String representing the email service endpoint.
     * @return true if email sending is successful, false otherwise.
     */
    public Boolean sendEmail(String email, String endpoint) {
        System.out.println("\n\n\n" + email + ", " + endpoint + "\n\n\n");
        return webClient.post()
                .uri("http://" + emailServiceIp + ":" + emailServicePort + "/email" + endpoint,
                        uriBuilder -> uriBuilder.queryParam("email", email).build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
}
