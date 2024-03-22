package com.kt.edu.thirdproject.common.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;

@Slf4j
public class RequestLoggingDecorator extends ServerHttpRequestDecorator {
    //@Autowired
    //private MonitoringService monitoringService;

    public RequestLoggingDecorator(ServerHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Flux<DataBuffer> getBody() {

        HttpMethod method = getMethod();
        if (method == HttpMethod.GET || method == HttpMethod.DELETE) {

            //monitoringService.sendRequestData(null, this);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        return super.getBody()
                .doOnNext(dataBuffer -> {
                    try {
                        Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());

                        //####### loggin to send monitoring
//                        monitoringService.sendRequestData(reqBody, this);

                    } catch (IOException e) {
                        log.error("error:{}", e.getMessage());
                    } finally {
                        try {
                            baos.close();
                        } catch (IOException e) {
                            log.error("error:{}", e.getMessage());
                        }
                    }
                });
    }
}