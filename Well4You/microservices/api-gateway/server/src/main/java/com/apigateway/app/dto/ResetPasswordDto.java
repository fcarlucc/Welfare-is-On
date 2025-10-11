package com.apigateway.app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {

    private String email;

    private String otp;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*])(?=\\S+$).{8,128}$",
            message = "Password must be 8-128 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;
}
