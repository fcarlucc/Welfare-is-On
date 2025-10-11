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
public class BookingDto implements Serializable {

    @NotNull(message = "coach id is null")
    private Long coachId;

    @NotNull(message = "coach id is null")
    private Long userId;

    @NotNull(message = "day is null")
    private LocalDate day;

    @NotNull(message = "slots is null")
    private SlotDto slot;

    private String notes;
}
