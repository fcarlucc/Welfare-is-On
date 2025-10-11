package com.apigateway.app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpDto {

    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull(message = "OTP code cannot be null")
    @Pattern(regexp = "^[0-9]{6}$", message = "OTP code must be a 6-digit number")
    private String otp;

    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", otp=" + otp +
                '}';
    }

}
