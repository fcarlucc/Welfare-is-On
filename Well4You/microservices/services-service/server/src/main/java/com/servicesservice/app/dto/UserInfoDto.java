package com.servicesservice.app.dto;

import com.servicesservice.app.model.enumerator.InterestName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Date dob;
    private String email;
    private List<InterestName> interests;
    private Boolean hasChildren;
    private Boolean hasElderlyParents;
    private String maritalStatusName;
    private String titleName;
    private Double longitude;
    private Double latitude;
}
