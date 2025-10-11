package com.coachservice.app.dto;

import com.coachservice.app.model.enumerator.DivisionName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoShowCaseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Long imageId;
    private DivisionName division;
    private LocationDto locationDto;
}
