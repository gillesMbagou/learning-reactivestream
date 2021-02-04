package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FluxAndMonoFactoryTest {
    private final Function<String, String> transformFirstToUpperCase = s -> Character.toUpperCase(s.charAt(0)) + s.substring(1);

    List<String> names = Arrays.asList("adam", "hannah", "anna", "jack", "jenny");

    @Test
    public void fluxUsingAccumulator() {
        Flux<String> stringFlux = Flux.fromStream(names.stream());

        StepVerifier
                .create(stringFlux
                        .map(transformFirstToUpperCase).log())
                .expectNext("Adam", "Hannah", "Anna", "Jack", "Jenny")
                .verifyComplete();

    }


}
