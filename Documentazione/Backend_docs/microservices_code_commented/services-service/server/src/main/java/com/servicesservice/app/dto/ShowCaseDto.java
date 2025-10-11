package com.servicesservice.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowCaseDto {
    private List<ServiceShowcaseDto> sections;
}
