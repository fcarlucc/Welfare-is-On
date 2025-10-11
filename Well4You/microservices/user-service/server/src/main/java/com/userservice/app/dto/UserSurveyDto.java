package com.userservice.app.dto;

import com.userservice.app.model.enumerator.InterestName;
import com.userservice.app.model.enumerator.MaritalStatusName;
import com.userservice.app.model.enumerator.TitleName;
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
public class UserSurveyDto {

    @NotNull(message = "Email cannot be null")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)\\.[A-Za-z]{2,4}$",
            message = "Invalid email format"
    )
    @Size(max = 100, message = "email length is too long")
    private String email;

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
                ", email='" + email + '\'' +
                ", hasChildren=" + hasChildren +
                ", hasElderlyParents=" + hasElderlyParents +
                ", maritalStatusName=" + maritalStatusName +
                ", titleName=" + titleName +
                ", interests=" + interests +
                '}';
    }
}