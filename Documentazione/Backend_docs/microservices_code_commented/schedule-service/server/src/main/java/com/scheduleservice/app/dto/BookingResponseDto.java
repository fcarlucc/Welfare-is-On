package com.scheduleservice.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponseDto implements Serializable {

    private LocalDate day;
    private SlotDto slot;
    private Long duration;
    private String notes;
}
