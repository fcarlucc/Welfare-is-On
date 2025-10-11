package com.otpservice.app.service;

import com.otpservice.app.exception.OtpNotFoundException;
import com.otpservice.app.repository.IOtpRepository;
import com.otpservice.app.model.Otp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

/**
 * Service class for managing OTP (One-Time Password) operations.
 */
@Service
@RequiredArgsConstructor
public class OtpService {

    private final IOtpRepository otpRepository;
    private static final int OTP_LENGTH = 6;
    private static final long OTP_VALIDITY_DURATION = 5 * 60 * 1000; // 5 minutes
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Generates a new OTP for the specified user ID or updates an existing OTP if already present.
     *
     * @param userId The ID of the user for whom the OTP is generated.
     * @return The generated OTP as a string.
     */
    public String generateOtp(Long userId) {
        String otp = generateNumericOtp();
        long expiryTime = System.currentTimeMillis() + OTP_VALIDITY_DURATION;
        Optional<Otp> existingOtp = otpRepository.findByUserId(userId);
        if (existingOtp.isPresent()) {
            existingOtp.get().setExpiryTime(expiryTime);
            existingOtp.get().setOtpCode(otp);
            update(existingOtp.get());
        } else {
            Otp otpEntity = new Otp(otp, userId, expiryTime);
            otpRepository.save(otpEntity);
        }
        return otp;
    }

    /**
     * Validates the provided OTP for the specified user ID.
     *
     * @param userId The ID of the user for whom the OTP is being validated.
     * @param otp    The OTP code to validate.
     * @return true if the OTP is valid and not expired, false otherwise.
     */
    public Boolean validateOtp(Long userId, String otp) {
        Optional<Otp> otpOptional = otpRepository.findByUserId(userId);
        if (otpOptional.isEmpty()) {
            return false;
        }
        Otp otpEntity = otpOptional.get();
        if (System.currentTimeMillis() > otpEntity.getExpiryTime()) {
            otpEntity.setOtpCode(null);
            update(otpEntity);
            return false;
        }
        if (otpEntity.getOtpCode().equals(otp)) {
            otpEntity.setOtpCode(null);
            update(otpEntity);
            return true;
        }
        return false;
    }

    /**
     * Retrieves an OTP entity by its ID.
     *
     * @param id The ID of the OTP entity.
     * @return The OTP entity.
     * @throws OtpNotFoundException if the OTP entity with the specified ID does not exist.
     */
    public Otp findById(Long id) {
        Optional<Otp> otp = otpRepository.findById(id);
        if (otp.isEmpty()) {
            throw new OtpNotFoundException("Otp not found");
        }
        return otp.get();
    }

    /**
     * Retrieves an OTP entity by user ID.
     *
     * @param userId The ID of the user associated with the OTP.
     * @return The OTP entity.
     * @throws OtpNotFoundException if the OTP entity for the specified user ID does not exist.
     */
    public Otp findByUserId(Long userId) {
        Optional<Otp> otp = otpRepository.findByUserId(userId);
        if (otp.isEmpty()) {
            throw new OtpNotFoundException("Otp not found");
        }
        return otp.get();
    }

    /**
     * Updates an existing OTP entity.
     *
     * @param otp The OTP entity to update.
     */
    public void update(Otp otp) {
        otpRepository.save(otp);
    }

    /**
     * Generates a numeric OTP of specified length.
     *
     * @return The generated OTP as a string.
     */
    private String generateNumericOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(secureRandom.nextInt(10));
        }
        return otp.toString();
    }
}
