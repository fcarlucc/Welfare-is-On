package com.servicesservice.app.dto;

import com.servicesservice.app.model.enumerator.PillarName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceShowcaseDto {

    private String title;
    private List<InfoShowCaseDto> infoShowCaseDto;
}
