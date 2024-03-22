package com.kt.edu.thirdproject.common.logging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.edu.thirdproject.common.transform.model.CommonHeader;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogDto {

    @JsonProperty("SERVICE")
    private String service;

    @JsonProperty("TYPE")
    private String type;

    @JsonProperty("CATEGORY")
    private String category;

    @JsonProperty("TRACE")
    private String trace;

    @JsonProperty("DATE")
    private String date;

    @JsonProperty("SOURCE")
    private String source;

    @JsonProperty("LOG-LEVEL")
    private String logLevel;

    @JsonProperty("HEADER")
    private CommonHeader header;

    @JsonProperty("MESAGE")
    private String message;
}