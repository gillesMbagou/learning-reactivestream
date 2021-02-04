package com.learnreactivespring.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RunWith(SpringRunner.class)
//@WebFluxTest // this class don't scan the @Component annotation
// that the raison we don't use it in functional web for test
@SpringBootTest
@AutoConfigureWebTestClient // create the bean
public class SampleHandlerFunctionTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void fluxHandlerApproach1() {
        Flux<Integer> responseBody = webTestClient
                .get()
                .uri("/functional/flux")
                .accept(APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(1, 2, 3, 4)
                .verifyComplete();
    }

}