package com.coachservice.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<String> interests;
    private Boolean hasChildren;
    private Boolean hasElderlyParents;
    private String maritalStatusName;
    private String titleName;
    private Double longitude;
    private Double latitude;
}
