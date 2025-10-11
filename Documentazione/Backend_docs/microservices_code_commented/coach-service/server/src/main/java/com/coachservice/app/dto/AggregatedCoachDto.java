package com.coachservice.app.dto;

import com.coachservice.app.model.enumerator.PillarName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregatedCoachDto {

    private PillarName specialization;
    private String phoneNumber;
    private String email;
}
