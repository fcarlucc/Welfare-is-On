package com.apigateway.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseDto implements Serializable {

    String responseString;

    @JsonCreator
    public ResponseDto(@JsonProperty("resonseString") String responseString) {
        this.responseString = responseString;
    }
}
