package com.otpservice.app.repository;

import com.otpservice.app.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing OTP (One-Time Password) entities in the database.
 */
public interface IOtpRepository extends JpaRepository<Otp, Long> {

    /**
     * Retrieves an OTP entity based on the user ID.
     *
     * @param userId The ID of the user associated with the OTP.
     * @return An Optional containing the OTP entity if found, otherwise empty.
     */
    Optional<Otp> findByUserId(Long userId);
}
