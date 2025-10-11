package com.servicesservice.app.dto;

import com.servicesservice.app.model.Service;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregatedServiceDto {

    private Long likes;
    private List<CommentDto> comments;
    private boolean isLiked;
    private boolean isPurchased;
    private Double distance;
    private String description;
    private Double price;
    private Integer discount;
    private LocationDto position;
    private String url;
}
