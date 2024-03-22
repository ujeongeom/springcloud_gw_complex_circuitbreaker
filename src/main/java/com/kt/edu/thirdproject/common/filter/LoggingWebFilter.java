package com.kt.edu.thirdproject.common.filter;

import com.kt.edu.thirdproject.common.logging.RequestLoggingDecorator;
import com.kt.edu.thirdproject.common.logging.ResponseLoggingDecorator;
import com.kt.edu.thirdproject.common.logging.StandardRequestLoggingDecorator;
import com.kt.edu.thirdproject.common.logging.StandardResponseLoggingDecorator;
import com.kt.edu.thirdproject.common.util.LogUtil;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@CircuitBreaker(name="apigw")
public class LoggingWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        final long startTime = System.currentTimeMillis();
        //List<String> header = exchange.getRequest().getHeaders().get("Content-Length");

        ServerWebExchangeDecorator exchangeDecorator = new ServerWebExchangeDecorator(exchange) {
            @Override
            public ServerHttpRequest getRequest() {
                return new StandardRequestLoggingDecorator(super.getRequest(), exchange);
            }

            @Override
            public ServerHttpResponse getResponse() {
                return new StandardResponseLoggingDecorator(super.getResponse(), exchange, startTime);
            }
        };
        return chain.filter(exchangeDecorator)
                .doOnSuccess(aVoid -> {

                    LogUtil.info(exchange, "[CTG:DEBUG] =======LoggingWebFilter========doOnSuccess======");

                })
                .doOnError(throwable -> {

                    LogUtil.error(exchange, "[CTG:DEBUG] =======LoggingWebFilter========doOnError======");

                });
    }

    /*private void fallback(String text, CallNotPermittedException ex){
        log.error("circuit OPEN:" + text);
    }*/

}