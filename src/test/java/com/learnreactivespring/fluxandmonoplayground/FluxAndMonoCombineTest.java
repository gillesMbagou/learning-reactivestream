package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoCombineTest {
    @Test
    public void combineUsingMerge() {
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");
        Flux<String> fluxMerged = Flux.merge(flux1, flux2);

        StepVerifier.create(fluxMerged.log())
                .expectSubscription()
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combineUsingConcat() {
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");
        Flux<String> fluxMerged = Flux.concat(flux1, flux2); // with concat, the flux2 started to emit after
        //flux1 is completed so it take to much time as operation

        StepVerifier.create(fluxMerged.log())
                .expectSubscription()
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combineUsingConcat_WithDelay() {
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
        Flux<String> fluxMerged = Flux.concat(flux1, flux2); // with concat, the flux2 started to emit after
        //flux1 is completed so it take to much time as operation

        StepVerifier.create(fluxMerged.log())
                .expectSubscription()
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combineUsingZip() {
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
        Flux<String> fluxMerged = Flux.zip(flux1, flux2, String::concat); // with concat, the flux2 started to emit after
        //flux1 is completed so it take to much time as operation

        StepVerifier.create(fluxMerged.log())
                .expectSubscription()
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    public void combineUsingMerge_withDelay() {
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
        Flux<String> fluxMerged = Flux.merge(flux1, flux2);

        StepVerifier.create(fluxMerged.log())
                .expectSubscription()
                .expectNextCount(6)
                //.expectNext("A", "B", "C","D", "E", "F")
                .verifyComplete();
    }
}
