package com.apigateway.app.dto;

import com.apigateway.app.model.enumerator.DivisionName;
import com.apigateway.app.model.enumerator.PillarName;
import com.apigateway.app.model.enumerator.RoleName;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachDto {

    @NotNull(message = "First name cannot be null")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "First name is invalid")
    @Size(max = 50, message = "First name length is too long")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Last name is invalid")
    @Size(max = 50, message = "Last name length is too long")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)\\.[A-Za-z]{2,4}$",
            message = "Invalid email format"
    )
    @Size(max = 100, message = "email length is too long")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*])(?=\\S+$).{8,128}$",
            message = "Password must be 8-128 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;

    private List<RoleName> roles;

    @Pattern(
            regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$",
            message = "Invalid phone number format"
    )
    private String phoneNumber;

    @NotNull(message = "longitude cannot be null")
    private Double longitude;

    @NotNull(message = "latitude cannot be null")
    private Double latitude;

    @NotNull(message = "specialization cannot be null")
    private PillarName specialization;

    @NotNull(message = "division cannot be null")
    private DivisionName division;

    @Override
    public String toString() {
        return "User{" +
                ", firstname='" + firstName + '\'' +
                ", lastname=" + lastName +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", specialization=" + specialization +
                ", division=" + division +
                '}';
    }
}
