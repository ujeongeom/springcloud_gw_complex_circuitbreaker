package com.kt.edu.thirdproject.common.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Order(-5)
@RequiredArgsConstructor
public class GwExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        // Add by jake
        if (ex.getClass() == CallNotPermittedException.class){
            log.error("error", ex);
            return Mono.empty();
        }
        // Header
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            //response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                GwErrorResponse gwErrorResponse = GwErrorResponse.defaultError(ex.getMessage());
                byte[] errorResponse = objectMapper.writeValueAsBytes(gwErrorResponse);

                return bufferFactory.wrap(errorResponse);
            } catch (Exception e) {
                log.error("error", e);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}