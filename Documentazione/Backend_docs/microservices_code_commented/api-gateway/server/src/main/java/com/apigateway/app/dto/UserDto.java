package com.apigateway.app.dto;

import java.util.Date;
import java.util.List;
import java.lang.Boolean;

import com.apigateway.app.model.enumerator.InterestName;
import com.apigateway.app.model.enumerator.MaritalStatusName;
import com.apigateway.app.model.enumerator.RoleName;
import com.apigateway.app.model.enumerator.TitleName;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "First name cannot be null")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "First name is invalid")
    @Size(max = 50, message = "First name length is too long")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Last name is invalid")
    @Size(max = 50, message = "Last name length is too long")
    private String lastName;

    @NotNull(message = "Date of birth cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

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

    @NotNull(message = "Interests cannot be null")
    private List<InterestName> interests;

    @NotNull(message = "HasChildren must be provided")
    private Boolean hasChildren;

    @NotNull(message = "HasElderlyParents must be provided")
    private Boolean hasElderlyParents;

    @NotNull(message = "Marital status must be provided")
    private MaritalStatusName maritalStatusName;

    @NotNull(message = "Title must be provided")
    private TitleName titleName;

    @Override
    public String toString() {
        return "User{" +
                ", firstname='" + firstName + '\'' +
                ", lastname=" + lastName +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", hasChildren=" + hasChildren +
                ", hasElderlyParents=" + hasElderlyParents +
                ", maritalStatusName=" + maritalStatusName +
                '}';
    }
}