package com.coachservice.app.dto;

import com.coachservice.app.model.enumerator.DivisionName;
import com.coachservice.app.model.enumerator.PillarName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachInfoTransferDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long imageId;
    private PillarName specialization;
    private String phoneNumber;
    private Double longitude;
    private Double latitude;
    private DivisionName division;
}
