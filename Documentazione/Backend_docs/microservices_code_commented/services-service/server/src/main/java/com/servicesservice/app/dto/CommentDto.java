package com.servicesservice.app.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private Long userId;

    private Long serviceId;

    private Date commentedAt;

    private String content;

    private String fullName;
}
