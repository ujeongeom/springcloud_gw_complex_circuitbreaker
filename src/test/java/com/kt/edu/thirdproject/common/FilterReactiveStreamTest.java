package com.kt.edu.thirdproject.common;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

class FilterReactiveStreamTest {


    List<String> cities = Arrays.asList("Chennai","Pune","Mumbai","Kolkatta");

    @Test
    void  filterTest1 () {
        Flux<String> cityFlux = Flux.fromIterable(cities);
        Flux<String> filteredCityFlux=cityFlux.filter(city->city.length()>7);
        StepVerifier.create(filteredCityFlux.log())
                .expectNext("Kolkatta")
                .verifyComplete();
    }

    @Test
    void  filterTest2 () {
        Flux<String> cityFlux = Flux.fromIterable(cities);
        Flux<String> filteredCityPFlux=cityFlux.filter(city->city.startsWith("P"));
        StepVerifier.create(filteredCityPFlux.log())
                .expectNext("Pune")
                .verifyComplete();
    }

    @Test
    void  filterTest3 () {
        Flux<String> cityFlux = Flux.fromIterable(cities);
        Flux<String> filteredCityPFlux=cityFlux.filter(city->city.contentEquals("Mumbai"));
        StepVerifier.create(filteredCityPFlux.log())
                .expectNext("Mumbai")
                .verifyComplete();
    }

    @Test
    void  filterTest4 () {
        Flux<String> cityFlux = Flux.fromIterable(cities);
        Flux<String> filteredCityPFlux=cityFlux.filter(city->city.endsWith("i"));
        StepVerifier.create(filteredCityPFlux.log())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void  filterTest5 () {
        Flux<String> cityFlux = Flux.fromIterable(cities);
        Flux<String> filteredCityPFlux=cityFlux.filter(city->city.isEmpty());
        StepVerifier.create(filteredCityPFlux.log())
                .expectNext()
                .verifyComplete();
    }
}
