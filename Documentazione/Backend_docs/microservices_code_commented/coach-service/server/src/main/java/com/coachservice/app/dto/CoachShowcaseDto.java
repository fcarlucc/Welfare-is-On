package com.coachservice.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachShowcaseDto {

    private String title;
    private List<InfoShowCaseDto> infoShowCaseDto;
}
