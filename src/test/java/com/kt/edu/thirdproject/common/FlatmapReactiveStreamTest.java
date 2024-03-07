package com.kt.edu.thirdproject.common;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FlatmapReactiveStreamTest {

        //Mock DB or external service
    private Mono< String > getEmpDetails (String  id ) {
        Map<String,String> map = new HashMap<>();
        map.put("1", "Joe");
        map.put("2", "Alex");
        map.put("3", "Marty");
        map.put("4", "Glory");
        map.put("5", "Ajay");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Mono.just(
                map.getOrDefault(id, "NotFound"));
    }

    @Test
    void  test () {
        List<String> listEmpId = Arrays.asList("1","2","3","4","5");
        //Transform the elements emitted by this Flux asynchronously into Publishers,then flatten these inner publishers into a single Flux through merging,which allow them to interleave.
        //DB or external service call that return a flux or mono
        Flux<String> flux=Flux.fromIterable(listEmpId)
                .flatMap(id->getEmpDetails(id))
                .log();
        StepVerifier.create(flux)
                .expectNextCount(5)
                .verifyComplete();
    }
}