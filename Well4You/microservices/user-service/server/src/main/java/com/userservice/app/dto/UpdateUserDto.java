package com.userservice.app.dto;

import com.userservice.app.model.enumerator.InterestName;
import com.userservice.app.model.enumerator.MaritalStatusName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    @NotNull(message = "user id cannot be null")
    private Long userId;

    @NotNull(message = "Interests cannot be null")
    private List<InterestName> interests;

    @NotNull(message = "HasChildren must be provided")
    private Boolean hasChildren;

    @NotNull(message = "HasElderlyParents must be provided")
    private Boolean hasElderlyParents;

    @NotNull(message = "Marital status must be provided")
    private MaritalStatusName maritalStatusName;

    @Override
    public String toString() {
        return "User{" +
                ", hasChildren=" + hasChildren +
                ", hasElderlyParents=" + hasElderlyParents +
                ", maritalStatusName=" + maritalStatusName +
                ", interests=" + interests +
                '}';
    }
}