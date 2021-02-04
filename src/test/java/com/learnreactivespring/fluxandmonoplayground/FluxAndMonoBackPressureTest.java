package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackPressureTest {

    @Test
    public void backPressureTest() {

        Flux<Integer> integerFlux = Flux.range(1, 10).log();

        StepVerifier.create(integerFlux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenCancel()
                .verify();

    }

    @Test
    public void backPressureProgrammaticallyTest() {

        Flux<Integer> integerFlux = Flux.range(1, 10).log();

        integerFlux.subscribe(
                element -> System.out.println("Element is :" + element),
                err -> System.err.println("err = " + err),
                () -> System.out.println("Done..."),
                subscription -> subscription.request(2));

    }

    @Test
    public void custom_backPressureTest() {
        Flux<Integer> infiniteFlux = Flux.range(1, 10).log();
        infiniteFlux.subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnNext(Integer value) {
                request(1);
                System.out.println("value received : " + value);
                if (value.equals(4)) cancel();
            }
        });

    }


}
