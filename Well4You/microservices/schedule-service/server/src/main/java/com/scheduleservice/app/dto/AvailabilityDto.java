package com.scheduleservice.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvailabilityDto implements Serializable {

    @NotNull(message = "coach id is null")
    private Long coachId;

    @NotNull(message = "day is null")
    private LocalDate day;

    @NotNull(message = "slots are null")
    private List<SlotDto> slots;
}
