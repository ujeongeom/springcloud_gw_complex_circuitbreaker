package com.kt.edu.thirdproject.common.logging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;

import com.kt.edu.thirdproject.common.monitoring.MonitoringService;
import com.kt.edu.thirdproject.common.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

@Slf4j
public class StandardRequestLoggingDecorator extends ServerHttpRequestDecorator {

    @Autowired
    private MonitoringService monitoringService;

    @Autowired
    private ServerWebExchange serverWebExchange;

    public StandardRequestLoggingDecorator(ServerHttpRequest delegate, ServerWebExchange exchange) {
        super(delegate);
        this.monitoringService = (MonitoringService) CommonBeanUtil.getBean(MonitoringService.class);
        this.serverWebExchange = exchange;
    }

    @Override
    public Flux<DataBuffer> getBody() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        return super.getBody()
                .doOnNext(dataBuffer -> {
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
                });
    }
}
