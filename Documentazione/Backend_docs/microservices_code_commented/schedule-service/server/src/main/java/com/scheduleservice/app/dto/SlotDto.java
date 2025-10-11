package com.scheduleservice.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SlotDto implements Serializable {

    @NotNull(message = "start time is null")
    private LocalTime startTime;

    @NotNull(message = "end time is null")
    private LocalTime endTime;
}