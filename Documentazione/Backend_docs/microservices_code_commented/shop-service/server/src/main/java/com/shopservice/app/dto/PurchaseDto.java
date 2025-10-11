package com.shopservice.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {

    @NotNull(message = "user id name cannot be null")
    private Long userId;

    @NotNull(message = "service id name cannot be null")
    private Long serviceId;

}
