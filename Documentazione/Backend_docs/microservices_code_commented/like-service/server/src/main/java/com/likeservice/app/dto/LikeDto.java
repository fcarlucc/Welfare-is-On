package com.likeservice.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {

    @NotNull(message = "user id name cannot be null")
    private Long userId;

    @NotNull(message = "service id name cannot be null")
    private Long serviceId;

}
