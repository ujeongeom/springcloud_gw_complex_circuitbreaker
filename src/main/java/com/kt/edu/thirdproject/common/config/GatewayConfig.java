package com.kt.edu.thirdproject.common.config;

import com.kt.edu.thirdproject.common.filter.LoggingWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    // Request/Response 로그 필터
    @Bean
    public LoggingWebFilter loggingWebFilter() {

        return new LoggingWebFilter();
    }

}