package com.otpservice.app.controller;

import com.otpservice.app.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling OTP (One-Time Password) generation and validation.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/otp")
public class OtpController {

    private final OtpService otpService;

    /**
     * Generates an OTP (One-Time Password) for the specified user.
     *
     * @param userId The ID of the user for whom the OTP is generated.
     * @return The generated OTP as a string.
     */
    @PostMapping("/generate")
    public String generateOtp(@RequestParam Long userId) {
        return otpService.generateOtp(userId);
    }

    /**
     * Validates an OTP (One-Time Password) entered by the user.
     *
     * @param userId The ID of the user for whom the OTP is validated.
     * @param otp    The OTP entered by the user for validation.
     * @return True if the OTP is valid, false otherwise.
     */
    @PostMapping("/validate")
    public Boolean validateOtp(@RequestParam Long userId, @RequestParam String otp) {
        return otpService.validateOtp(userId, otp);
    }
}
