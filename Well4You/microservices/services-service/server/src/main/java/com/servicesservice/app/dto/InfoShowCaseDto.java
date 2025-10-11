package com.servicesservice.app.dto;

import com.servicesservice.app.model.enumerator.PillarName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoShowCaseDto {

    private Long id;
    private String title;
    private Long imageId;
    private PillarName pillarName;
}
