package com.kt.edu.thirdproject.common;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class FluxTest {

    @Test
    void testFlux1() {
        //Create a Flux that completes without emitting any item.
        Flux.empty();
    }

    @Test
    void testFlux2() {
        //Create a new Flux that will only emit a single element then onComplete.
        Flux<String> flux= Flux.just("Spring 5");
        flux.subscribe(System.out::println);
    }

    @Test
    void testFlux3() {
        //Create a Flux that emits the provided elements and then completes.
        Flux<String> flux= Flux.just("Spring MVC","Spring Boot","Spring Web");
        flux.subscribe(System.out::println);
    }

    @Test
    void testFlux4() {
        //Create a Flux that emits the items contained in the provided array.
        Flux<String> flux = Flux.fromArray(new String[]{"A", "B", "C"});
        flux.subscribe(System.out::println);
    }
    @Test
    void testFlux5() {

        //Create a Flux that emits the items contained in the provided Iterable.A new iterator will be created for each subscriber.
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Flux<Integer> flux=Flux.fromIterable(list);
        flux.subscribe(System.out::println);
    }

    @Test
    void testFlux6() {
        //Concatenate emissions of this Flux with the provided Publisher (no interleave).
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Flux<Integer> flux=Flux.fromIterable(list)
                .concatWith(Flux.just(6,7,8));
        flux.subscribe(System.out::println);
    }

    @Test
    void testFlux7() {
        //Create a Flux that terminates with the specified error immediately afterbeing subscribed to.
        Flux<String> flux= Flux.error(new RuntimeException("Error Occurred"));
        //flux.subscribe(System.out::println);
    }
}
        /*Flux<Integer> seq = Flux.just(1, 2, 3);

        seq.subscribe(new Subscriber<>() {

        private Subscription subscription;

        @Override
        public void onSubscribe(Subscription s) {

            System.out.println("Subscriber.onSubscribe");
            this.subscription = s;
            this.subscription.request(1); // Publisher에 데이터 요청

        }

        @Override
        public void onNext(Integer i) {
            System.out.println("Subscriber.onNext: " + i);
            this.subscription.request(1); // Publisher에 데이터 요청
        }


        @Override
        public void onError(Throwable t) {
            System.out.println("Subscriber.onError: " + t.getMessage());
        }


        @Override
        public void onComplete() {
            System.out.println("Subscriber.onComplete");
        }
    });*/
