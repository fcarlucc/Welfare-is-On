package com.scheduleservice.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregatedCoachDto {

    private String specialization;
    private String phoneNumber;
    private String email;
}
