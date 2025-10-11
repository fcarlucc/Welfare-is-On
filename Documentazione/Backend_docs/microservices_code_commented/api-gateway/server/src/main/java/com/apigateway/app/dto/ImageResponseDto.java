package com.apigateway.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageResponseDto {

    String responseString;
    Long imageId;
}
