package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static reactor.core.publisher.Flux.error;
import static reactor.core.publisher.Flux.just;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = just("Spring", "Spring Boot", "Reactive Spring")
/*
                .concatWith(error(new RuntimeException("Exception Occured")))
*/
                .concatWith(just("Functional Programming"))
                .log();
        stringFlux
                .subscribe(System.out::println,
                        System.err::println,
                        () -> System.out.println("No Data at all. Completed...."));
    }

    @Test
    public void fluxTestElements_withoutError() {
        Flux<String> stringFlux = just("Spring", "Spring Boot", "Reactive Spring")
                .log();
        // reactor test module
        StepVerifier
                .create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .verifyComplete();
        //.expectComplete();


    }

    @Test
    public void fluxTestElements_withError() {
        Flux<String> stringFlux = just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(error(new RuntimeException("Exception Occured")))

                .log();
        // reactor test module
        StepVerifier
                .create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .expectError(RuntimeException.class)
                .verify();


    }

    @Test
    public void fluxTestElements_withErrorMessage() {
        Flux<String> stringFlux = just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(error(new RuntimeException("Exception Occured")))

                .log();
        // reactor test module
        StepVerifier
                .create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .expectErrorMessage("Exception Occured")
                .verify();
    }

    @Test
    public void monoTest_withoutError() {
        Mono<String> spring = Mono.justOrEmpty("Spring");
        StepVerifier.create(spring.log())
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void monoTest_withError() {
        StepVerifier.create((Mono.error(new RuntimeException("Mono exception Occured"))).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
