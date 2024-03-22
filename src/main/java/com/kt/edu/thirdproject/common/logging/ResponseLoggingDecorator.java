package com.kt.edu.thirdproject.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;

@Slf4j
public class ResponseLoggingDecorator extends ServerHttpResponseDecorator {

    private long startTime;

    public ResponseLoggingDecorator(ServerHttpResponse delegate, long startTime) {
        super(delegate);
        this.startTime = startTime;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> bodyBuffer) {
        Flux<DataBuffer> buffer = Flux.from(bodyBuffer);
        return super.writeWith(buffer.doOnNext(dataBuffer -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
                //String resBody = IOUtils.toString(baos.toByteArray(), "UTF-8");

                //####### loggin to send monitoring
//                monitoringService.sendResponseData(resBody, this, startTime);

            } catch (IOException e) {
                log.error(e.getMessage());
            } finally {
                try {
                    baos.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }));
    }


}