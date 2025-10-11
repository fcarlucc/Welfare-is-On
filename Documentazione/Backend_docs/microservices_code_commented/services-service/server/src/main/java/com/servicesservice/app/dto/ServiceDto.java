package com.servicesservice.app.dto;

import com.servicesservice.app.model.enumerator.PillarName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {

    @NotNull(message = "Description cannot be null")
    @Size(max = 100, message = "Description length is too long")
    private String description;

    @NotNull(message = "Title cannot be null")
    @Size(max = 20, message = "Title length is too long")
    private String title;

    @NotNull(message = "Price cannot be null")
    private Double price;

    @NotNull(message = "discount cannot be null")
    private Integer discount;

    @NotNull(message = "url cannot be null")
    @URL(message = "not valid url")
    private String url;

    @NotNull(message = "pillar cannot be null")
    private PillarName pillarName;

    private Double longitude;

    private Double latitude;
}
