package com.kt.edu.thirdproject.common.logging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;

import com.kt.edu.thirdproject.common.monitoring.MonitoringService;
import com.kt.edu.thirdproject.common.util.LogUtil;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class StandardResponseLoggingDecorator extends ServerHttpResponseDecorator {

    @Autowired
    private MonitoringService monitoringService;

    @Autowired
    private ServerWebExchange serverWebExchange;

    public StandardResponseLoggingDecorator(ServerHttpResponse delegate, ServerWebExchange serverWebExchange, long startTime) {
        super(delegate);
        this.monitoringService = (MonitoringService) CommonBeanUtil.getBean(MonitoringService.class);
        this.serverWebExchange = serverWebExchange;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> bodyBuffer) {
        Flux<DataBuffer> buffer = Flux.from(bodyBuffer);
        return super.writeWith(buffer.doOnNext(dataBuffer -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
            } catch (IOException e) {
                LogUtil.error(serverWebExchange, "[CTG:DEBUG] error : {}", e.getMessage());
            } finally {
                try {
                    baos.close();
                } catch (IOException e) {
                    LogUtil.error(serverWebExchange, "[CTG:DEBUG] error : {}", e.getMessage());
                }
            }
        }));
    }
}