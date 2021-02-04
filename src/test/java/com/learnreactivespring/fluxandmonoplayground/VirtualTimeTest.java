package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

public class VirtualTimeTest {
    @Test
    public void testingWithoutVirtualTime() {
        Flux<Integer> integerFlux = Flux.interval(Duration.ofMillis(100))
                .map(Long::intValue)
                .take(3);
        StepVerifier.create(integerFlux.log())
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete();
    }

    @Test
    public void testingWithVirtualTime() {

        VirtualTimeScheduler.getOrSet(); //enable virtual clock
        // for us it's don't use the clock of our machine (real)
        Flux<Integer> integerFlux = Flux.interval(Duration.ofMillis(100))
                .map(Long::intValue)
                .take(3);

        StepVerifier.withVirtualTime(integerFlux::log)
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(3))// this method activate the virtual time
                .expectNext(0, 1, 2)
                .verifyComplete();

    }
}
