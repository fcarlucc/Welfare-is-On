package com.notificationservice.app.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Service class responsible for sending various types of emails.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Sends a general email.
     *
     * @param email   The recipient's email address.
     * @param text    The email content.
     * @param subject The email subject.
     * @throws MessagingException If there is an error while preparing or sending the email.
     */
    public void generalEmail(String email, String text, String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setTo(email);
        messageHelper.setSubject(subject);
        messageHelper.setText(text);
        mailSender.send(mimeMessage);
    }

    /**
     * Sends a sign-in email with OTP.
     *
     * @param email The recipient's email address.
     * @param otp   The OTP (One Time Password) for authentication.
     * @throws MessagingException If there is an error while preparing or sending the email.
     */
    public void sendSignInEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String dateStr = dateFormat.format(new Date());
        String timeStr = timeFormat.format(new Date());

        messageHelper.setTo(email);
        messageHelper.setSubject("Sign In");
        messageHelper.setText("You have made a new login on " + dateStr + " at " + timeStr + ". If it's not you, you may be at risk of hacking.\nPlease enter the following verification code for 2FA:\n\n" + otp + "\n\n\nHai effettuato un nuovo accesso il " + dateStr + " alle " + timeStr + ", se non sei tu potresti essere sotto esposto a rischi di hackeraggio.\nInserisci il seguente codice per la verifica 2FA\n\n" + otp);
        mailSender.send(mimeMessage);
    }

    /**
     * Sends an authentication failure notification email.
     *
     * @param email The recipient's email address.
     * @throws MessagingException If there is an error while preparing or sending the email.
     */
    public void sendAuthFailure(String email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String dateStr = dateFormat.format(new Date());
        String timeStr = timeFormat.format(new Date());

        messageHelper.setTo(email);
        messageHelper.setSubject("Authentication Failure");
        messageHelper.setText("You attempted to make a new login on " + dateStr + " at " + timeStr + ". If it's not you, you may be at risk of hacking.\n\n\nHai tentato di effettuare un nuovo accesso il " + dateStr + " alle " + timeStr + ", se non sei tu potresti essere sotto esposto a rischi di hackeraggio.\n");
        mailSender.send(mimeMessage);
    }

    /**
     * Sends a password reset email with OTP.
     *
     * @param email The recipient's email address.
     * @param otp   The OTP (One Time Password) for resetting the password.
     * @throws MessagingException If there is an error while preparing or sending the email.
     */
    public void sendResetPassword(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setTo(email);
        messageHelper.setSubject("Password Reset");
        messageHelper.setText("You have requested a password reset. Use the following OTP code to reset your password: \n\n" + otp + "\n\n\nHai richiesto il ripristino della password. Utilizza il seguente codice OTP per resettare la tua password: \n\n" + otp);
        mailSender.send(mimeMessage);
    }

    /**
     * Sends an email for verifying email address with OTP.
     *
     * @param email The recipient's email address.
     * @param otp   The OTP (One Time Password) for email verification.
     * @throws MessagingException If there is an error while preparing or sending the email.
     */
    public void verifyEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setTo(email);
        messageHelper.setSubject("Verify Email");
        messageHelper.setText("Use the following OTP code to verify your email: \n\n" + otp + "\n\n\nUtilizza il seguente codice OTP per verificare la tua email: \n\n" + otp);
        mailSender.send(mimeMessage);
    }

    /**
     * Sends an email to notify about changes made to the user's profile.
     *
     * @param email The recipient's email address.
     * @throws MessagingException If there is an error while preparing or sending the email.
     */
    public void sendChangesAdviseAboutProfile(String email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String dateStr = dateFormat.format(new Date());
        String timeStr = timeFormat.format(new Date());

        messageHelper.setTo(email);
        messageHelper.setSubject("Profile updated");
        messageHelper.setText("You have successfully updated your personal data.\nOn " + dateStr + " at " + timeStr + ".\n\n\nHai correttamnte aggiornato i tuoi dati personali.\nIl " + dateStr + " alle " + timeStr + ".\n");
        mailSender.send(mimeMessage);
    }

    /**
     * Sends an email to confirm the change of email address with OTP.
     *
     * @param email The recipient's new email address.
     * @param otp   The OTP (One Time Password) for confirming email change.
     * @throws MessagingException If there is an error while preparing or sending the email.
     */
    public void sendConfirmChangeEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setTo(email);
        messageHelper.setSubject("Email Change");
        messageHelper.setText("Use the following OTP code to change your email: \n\n" + otp + "\n\n\nUtilizza il seguente codice OTP per cambiare la tua email: \n\n" + otp);
        mailSender.send(mimeMessage);
    }
}
