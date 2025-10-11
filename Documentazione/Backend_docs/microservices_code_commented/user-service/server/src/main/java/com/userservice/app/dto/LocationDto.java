package com.userservice.app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    @NotNull(message = "Email cannot be null")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)\\.[A-Za-z]{2,4}$",
            message = "Invalid email format"
    )
    @Size(max = 100, message = "email length is too long")
    private String email;

    @NotNull(message = "longitude cannot be null")
    private Double longitude;

    @NotNull(message = "latitude cannot be null")
    private Double latitude;

    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}