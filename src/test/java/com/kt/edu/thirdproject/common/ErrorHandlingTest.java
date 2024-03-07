package com.kt.edu.thirdproject.common;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ErrorHandlingTest {

    @Test
    void  testError1 () {
        Flux<String> f1= Flux.just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Some Error")))
                .concatWith(Flux.just("D"));
        StepVerifier.create(f1.log())
                .expectNext("A","B","C")
                .expectError()
                .verify();
    }

    @Test
    void  doOnErrorTest () {
        Flux<String> f1= Flux.just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Some Error")))
                .doOnError((err)->System.out.println("Some error occurred "+err));
        StepVerifier.create(f1.log())
                .expectNextCount(3)
                .expectError()
                .verify();
    }

    @Test
    public void onErrorReturn() {
        Flux<String> f1= Flux.just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Some Error")))
                .onErrorReturn("default value");
        StepVerifier.create(f1.log())
                .expectNextCount(3)
                .expectNext("some default value")
                .verifyComplete();
    }
}