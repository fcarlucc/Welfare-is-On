package com.scheduleservice.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DailySlotsDto implements Serializable {

    List<SlotDto> bookedSlots;
    List<SlotDto> alreadySetSlots;
}
