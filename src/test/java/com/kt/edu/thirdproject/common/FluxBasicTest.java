package com.kt.edu.thirdproject.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FluxBasicTest {

    @Test
    public void flux_just_consumer() {
        List<String> names = new ArrayList<String>();
        Flux<String> flux = Flux.just("자바","스칼라","파이썬").log();
        flux.subscribe(s -> {
            System.out.println("시퀀스 수신 : " + s);
            names.add(s);
        });
        assertEquals(names, Arrays.asList("자바","스칼라","파이썬"));
    }

    // 람다 식을 반복문으로 변경해 보기
    @Test
    public void ArrayTest() {
        List<String> names = new ArrayList<String>();
        names.add("자바");
        names.add("스칼라");
        names.add("파이썬");
        // for 문
        for(String s : names){
            System.out.println("시퀀스 수신 : " + s);
        }

        // while 문
        Iterator iter = names.iterator();
        while(iter.hasNext()){
            System.out.println("Iter 시퀀스 수신 : " + iter.next());
        }
    }

    // Filter와 Collect 사용하기
    @Test
    public void ArrayStreamTest() {
        List<String> names = new ArrayList<String>();
        names.add("자바");
        names.add("스칼라");
        names.add("파이썬");
        List<String> FilteredList = names.stream()
                .filter(s-> s.equals("자바"))
                .collect(Collectors.toList());
        System.out.println("FilteredList : " + FilteredList);

        Flux<String> flux = Flux.fromIterable(names).log();
        flux.subscribe(System.out::println);
        //flux.subscribe(names::add);
        //assertEquals(names, Arrays.asList("자바","스칼라","파이썬"));
    }

    //Member Class 만들고 age 인 값만 별도 List 만들기
    // https://develop-writing.tistory.com/137
    @Test
    public void ArrayStreamTest2() {
        List<Member> names = new ArrayList<>();
        names.add(new Member("자바",20));
        names.add(new Member("스칼라",30));
        names.add(new Member("파이썬",40));

        List<Member> FilteredList = names.stream()
                .filter(s-> s.getAge() > 30)
                .collect(Collectors.toList());
        System.out.println("FilteredList : " + FilteredList);

        List<Integer> ageList = names.stream()
                .map(s -> s.getAge())
                .collect(Collectors.toList());
        System.out.println("ageList : " + ageList);

        List<String> nameList = names.stream()
                .map(Member::getName)
                .collect(Collectors.toList());
        System.out.println("nameList : " + nameList);
    }

    @Test
    public void SubscriberTest() {
        Flux.range(1, 3) // 1부터 3까지 세개의 이벤트를 발생시키는 Publisher
                .subscribe(new Subscriber<>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        System.out.println("[Subscriber] onSubscribe");
                        subscription.request(1);
                    }

                    @Override
                    public void onNext(Integer item) {
                        System.out.println("[Subscriber] onNext : " + item);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("[Subscriber] onError : " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("[Subscriber] onComplete");
                    }
                });
    }
    @Test
    public void FluxDefaultTest() {
        Flux.empty().defaultIfEmpty(10).subscribe(System.out::println);
    }

    @Test
    public void flatMapMany(){
        Mono.just(1)
                .flatMapMany(item -> Flux.just(3, 2, 1))
                .subscribe(
                        item -> System.out.println("[Subscriber] onNext : " + item),
                        e -> System.out.println("[Subscriber] onError : " + e.getMessage()),
                        () -> System.out.println("[Subscriber] onComplete")
                );
    }

    @Test
    public void zip(){
        var flux1 = Flux.range(1, 15);
        var flux2 = Flux.range(1, 10).map(it -> it * 10);
        var flux3 = Flux.range(1, 5).map(it -> it * 100);
        Flux.zip(flux1, flux2, flux3)
                .subscribe(item -> System.out.println("[Subscriber] onNext : " + item),
                        e -> System.out.println("[Subscriber] onError : " + e.getMessage()),
                        () -> System.out.println("[Subscriber] onComplete"));
    }
}

@Data
@AllArgsConstructor
class Member {
    private  String name;
    private int age;

}