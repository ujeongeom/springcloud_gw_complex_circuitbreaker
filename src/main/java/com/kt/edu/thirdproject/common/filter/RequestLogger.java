package com.kt.edu.thirdproject.common.filter;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class RequestLogger {/*implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return Mono
                .just(exchange)
                .doOnNext(
                        $ -> Mono.fromRunnable(
                                        () -> {
                                            try { Thread.sleep(3000); System.out.println("뿡"); }
                                            catch (Exception e) {}
                                        })
                                .subscribeOn(Schedulers.boundedElastic())
                                .subscribe())
                .flatMap(chain::filter);
    } */
}