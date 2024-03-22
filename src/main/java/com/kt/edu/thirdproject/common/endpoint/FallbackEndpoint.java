package com.kt.edu.thirdproject.common.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
public class FallbackEndpoint {

    @RequestMapping("/circuitbreakerfallback")
    public String circuitbreakerfallback() {

        return "This is a fallback";
    }
/*
    @RequestMapping("/fallback")
    public Flux<Void> fallback() {
        log.info("Fallback");
        return Flux.empty();
    }*/
}