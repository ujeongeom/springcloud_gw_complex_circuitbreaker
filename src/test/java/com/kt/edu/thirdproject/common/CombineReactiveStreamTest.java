package com.kt.edu.thirdproject.common;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

class CombineReactiveStreamTest {

        //Combine Using merge
    @Test
    public void mergeTest() {
        Flux<String> f1 = Flux.just("A","B","C");
        Flux<String> f2 = Flux.just("X","Y","Z");
        Flux<String> combFlux = Flux.merge(f1,f2);
        StepVerifier.create(combFlux.log())
                .expectNext("A","B","C","X","Y","Z")
                .verifyComplete();
    }

    @Test
    public void mergewithdelayTest() {//it takes 3 seconds
        Flux<String> f1 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> f2 = Flux.just("X","Y","Z").delayElements(Duration.ofSeconds(1));
        Flux<String> combFlux = Flux.merge(f1,f2);
        StepVerifier.create(combFlux.log())
                .expectNextCount(6)
                .verifyComplete();
    }
        //Combine using Concat
    @Test
    public void combineWithConcatTest1() {
        Flux<String> f1 = Flux.just("A","B","C");
        Flux<String> f2 = Flux.just("X","Y","Z");
        Flux<String> combFlux = Flux.concat(f1,f2);

        StepVerifier.create(combFlux.log())
                .expectNext("A","B","C","X","Y","Z")
                .verifyComplete();
    }

    @Test
    public void combineWithConcatTest2() {
        Flux<String> f1 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> f2 = Flux.just("X","Y","Z").delayElements(Duration.ofSeconds(1));
        Flux<String> combFlux = Flux.concat(f1,f2);
        StepVerifier.create(combFlux.log())
                .expectNext("A","B","C","X","Y","Z")
                .verifyComplete();
    }
        //Combine using zip
    @Test
    public void combineWithZip() {
        Flux<String> f1 = Flux.just("A","B","C");
        Flux<String> f2 = Flux.just("X","Y","Z");
        Flux<Tuple2<String, String>> zip=Flux.zip(f1, f2);

        StepVerifier.create(zip.log())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void combineWithZipWith() {
        Flux<String> f1 = Flux.just("A","B","C");
        Flux<String> f2 = Flux.just("X","Y","Z");
        Flux<Tuple2<String, String>> zip=f1.zipWith(f2);
        StepVerifier.create(zip.log())
                .expectNextCount(3)
                .verifyComplete();
    }
}