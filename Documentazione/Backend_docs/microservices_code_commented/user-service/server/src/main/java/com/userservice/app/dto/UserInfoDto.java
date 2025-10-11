package com.userservice.app.dto;

import com.userservice.app.model.enumerator.InterestName;
import com.userservice.app.model.enumerator.MaritalStatusName;
import com.userservice.app.model.enumerator.TitleName;
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
    private List<InterestName> interests;
    private Boolean hasChildren;
    private Boolean hasElderlyParents;
    private MaritalStatusName maritalStatusName;
    private TitleName titleName;
    private Double longitude;
    private Double latitude;
    private Double savedMoney;
}
