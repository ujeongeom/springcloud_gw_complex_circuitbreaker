package com.kt.edu.thirdproject.common.config;

import com.kt.edu.thirdproject.common.exception.JwtAuthenticationException;
import com.kt.edu.thirdproject.common.security.JwtService;
import com.kt.edu.thirdproject.common.security.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
//@RequiredArgsConstructor
class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private  JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .cast(JwtToken.class)
                .filter(jwtToken -> jwtService.isTokenValid(jwtToken.getToken()))
                .map(jwtToken -> jwtToken.withAuthenticated(true))
                .switchIfEmpty(Mono.error(new JwtAuthenticationException("Invalid token.")));
    }
}