package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoErrorTest {

    @Test
    public void fluxErrorHandling() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Error Occured ...")))
                .concatWith(Flux.just("D", "E"))
                .onErrorResume((e) -> {
                    System.err.println("Exception" + e.getMessage());
                    return Flux.just("default", "default2");
                });
        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("default", "default2")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandling_OnErrorReturn() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Error Occured ...")))
                .concatWith(Flux.just("D", "E"))
                .onErrorReturn("default");
        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandling_OnErrorMap() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Error Occured ...")))
                .concatWith(Flux.just("D", "E"))
                .onErrorMap(CustomException::new);
        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectError(CustomException.class)
                .verify();
    }
}
