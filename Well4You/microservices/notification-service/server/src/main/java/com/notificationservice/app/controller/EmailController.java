package com.notificationservice.app.controller;

import com.notificationservice.app.dto.EmailDto;
import com.notificationservice.app.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    /**
     * Sends a general email.
     *
     * @param emailDto EmailDto containing email details (email, text, subject).
     * @return true if the email was sent successfully, false otherwise.
     * @throws MessagingException if an error occurs during email sending.
     */
    @PostMapping("/general")
    public boolean sendGeneralEmail(@RequestBody EmailDto emailDto) throws MessagingException {
        try {
            emailService.generalEmail(emailDto.getEmail(), emailDto.getText(), emailDto.getSubject());
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    /**
     * Sends a sign-in email with OTP.
     *
     * @param email String representing the recipient's email address.
     * @param otp   String representing the OTP (One-Time Password).
     * @return true if the email was sent successfully, false otherwise.
     */
    @PostMapping("/signin")
    public boolean sendSignInEmail(@RequestParam String email, @RequestParam String otp) {
        try {
            emailService.sendSignInEmail(email, otp);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    /**
     * Sends an authentication failure email.
     *
     * @param email String representing the recipient's email address.
     * @return true if the email was sent successfully, false otherwise.
     */
    @PostMapping("/auth-fail")
    public boolean sendAuthFailureEmail(@RequestParam String email) {
        try {
            emailService.sendAuthFailure(email);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    /**
     * Sends a reset password email with OTP.
     *
     * @param email String representing the recipient's email address.
     * @param otp   String representing the OTP (One-Time Password).
     * @return true if the email was sent successfully, false otherwise.
     */
    @PostMapping("/reset-password")
    public boolean sendResetPasswordEmail(@RequestParam String email, @RequestParam String otp) {
        try {
            emailService.sendResetPassword(email, otp);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    /**
     * Sends notification about changes related to the user profile.
     *
     * @param email String representing the recipient's email address.
     * @return true if the email was sent successfully, false otherwise.
     */
    @PostMapping("/notify-profile")
    public boolean sendProfileChangeNotificationEmail(@RequestParam String email) {
        try {
            emailService.sendChangesAdviseAboutProfile(email);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    /**
     * Sends an email to confirm changing the email address with OTP.
     *
     * @param email String representing the recipient's email address.
     * @param otp   String representing the OTP (One-Time Password).
     * @return true if the email was sent successfully, false otherwise.
     */
    @PostMapping("/change-email")
    public boolean sendConfirmChangeEmail(@RequestParam String email, @RequestParam String otp) {
        try {
            emailService.sendConfirmChangeEmail(email, otp);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    /**
     * Verifies an email address with OTP.
     *
     * @param email String representing the recipient's email address.
     * @param otp   String representing the OTP (One-Time Password).
     * @return true if the email was verified successfully, false otherwise.
     */
    @PostMapping("/verify-email")
    public boolean verifyEmail(@RequestParam String email, @RequestParam String otp) {
        System.out.println("email = " + email + ", otp = " + otp);
        try {
            emailService.verifyEmail(email, otp);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
}
