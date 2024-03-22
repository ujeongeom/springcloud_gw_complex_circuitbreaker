package com.kt.edu.thirdproject.common.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
public class GwErrorResponse {

    private String errorMessage;
    private LocalDateTime localDateTime;
    private Map<String, Object> erroInfo = new HashMap<>();

    public GwErrorResponse(String errorMessage, LocalDateTime localDateTime) {
        this.errorMessage = errorMessage;
        this.localDateTime = localDateTime;
    }

    public static GwErrorResponse defaultError(String errorMessage){
        return new GwErrorResponse(errorMessage, LocalDateTime.now());
    }
}