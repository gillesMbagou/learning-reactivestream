package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoInfinite {

    @Test
    //@RepeatedTest(value = 3, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("RepeatingTest_3 times")
    public void finiteSequenceMap() {
        Flux<Integer> integerFlux = Flux.interval(Duration.ofMillis(100))
                .map(Long::intValue)
                .take(3)
                .log();
        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete();
    }
}
