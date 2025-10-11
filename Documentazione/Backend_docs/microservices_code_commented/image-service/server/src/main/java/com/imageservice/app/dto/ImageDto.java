package com.imageservice.app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    @NotNull(message = "User ID cannot be null")
    private String userId;

    @NotNull(message = "OTP code cannot be null")
    @Pattern(regexp = "^[0-9]{6}$", message = "OTP code must be a 6-digit number")
    private String image;

    @Override
    public String toString() {
        return "User{" +
                ", user id='" + userId + '\'' +
                ", image=" + image +
                '}';
    }

}
