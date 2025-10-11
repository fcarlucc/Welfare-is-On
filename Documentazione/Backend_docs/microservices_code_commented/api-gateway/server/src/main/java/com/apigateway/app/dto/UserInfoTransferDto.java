package com.apigateway.app.dto;

import com.apigateway.app.model.enumerator.InterestName;
import com.apigateway.app.model.enumerator.MaritalStatusName;
import com.apigateway.app.model.enumerator.TitleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoTransferDto {

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

    @Override
    public String toString() {
        return "User{" +
                ", firstname='" + firstName + '\'' +
                ", lastname=" + lastName +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", hasChildren=" + hasChildren +
                ", hasElderlyParents=" + hasElderlyParents +
                ", maritalStatusName=" + maritalStatusName +
                '}';
    }
}