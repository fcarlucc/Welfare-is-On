package com.commentservice.app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @NotNull(message = "user id name cannot be null")
    private Long userId;

    @NotNull(message = "service id name cannot be null")
    private Long serviceId;

    @NotNull(message = "creation date cannot be null")
    private OffsetDateTime commentedAt;

    @NotNull(message = "content date cannot be null")
    @Size(max = 100)
    private String content;

    @NotNull(message = "content date cannot be null")
    @Size(max = 50)
    // @Pattern(regexp = "^([A-Z][a-z]+(\\s[A-Z][a-z]+)*){1,3}(?:\\s(-|')\\s([A-Z][a-z]+))?$",
    //         message = "Invalid full name format. Please enter a name with valid characters and structure.")
    private String fullName;
}
