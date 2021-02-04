package com.learnreactivespring.fluxandmonoplayground;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotAndColdPublisherTest {

    @SneakyThrows
    @Test
    public void coldPublisher() {
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F").delayElements(Duration.ofSeconds(1));

        stringFlux.subscribe(s -> System.out.println("Subscriber 1: " + s));//emits the value from beginning

        Thread.sleep(2000);
        stringFlux.subscribe(s -> System.out.println("Subscriber 2: " + s));
        // like http request everytime we call
        // the request it emits a new response from the beginning.

        Thread.sleep(4000);

    }

    @SneakyThrows
    @Test
    public void hotPublisherTest() {

        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F", "G").delayElements(Duration.ofSeconds(1));

        ConnectableFlux<String> connectableFlux = stringFlux.publish();
        connectableFlux.connect();
        connectableFlux.subscribe(s -> System.out.println("Subscriber 1 : " + s));
        Thread.sleep(3000);
        connectableFlux.subscribe(s -> System.out.println("Subscriber 2 : " + s));
        Thread.sleep(4000);


    }
}
