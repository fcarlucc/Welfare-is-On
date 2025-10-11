package com.otpservice.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents an OTP (One-Time Password) entity for user authentication.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "otp", schema = "otpservice")
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id", nullable = false, updatable = false)
    private Long id;

    @Column(unique = true)
    private String otpCode;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private long expiryTime;

    /**
     * Constructs an OTP instance with the specified parameters.
     *
     * @param otpCode    The OTP code generated for authentication.
     * @param userId     The ID of the user associated with this OTP.
     * @param expiryTime The expiration time of the OTP in milliseconds since epoch.
     */
    public Otp(String otpCode, Long userId, long expiryTime) {
        this.otpCode = otpCode;
        this.userId = userId;
        this.expiryTime = expiryTime;
    }

    /**
     * Returns a string representation of the OTP instance.
     *
     * @return A string representation containing the OTP ID, code, user ID, and expiry time.
     */
    @Override
    public String toString() {
        return "Otp{" +
                "id=" + id +
                ", otpCode='" + otpCode + '\'' +
                ", userId=" + userId +
                ", expiryTime=" + expiryTime +
                '}';
    }
}
