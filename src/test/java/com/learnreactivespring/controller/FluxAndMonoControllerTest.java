package com.learnreactivespring.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE;

@RunWith(SpringRunner.class)
@WebFluxTest
        // scans all class annoteted with @RestController, @Controller not
        // with @Component, @Service,@Repository etc..
class FluxAndMonoControllerTest {
    @Autowired
    WebTestClient webTestClient; //This is a class for non blocking end-point
    //we need a non blocking client like TestWebTemplate

    @Test
    public void flux_approach1() {
        Flux<Integer> responseBody = webTestClient
                .get()
                .uri("/flux")
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

    @Test
    public void flux_approach2() {
        webTestClient
                .get()
                .uri("/flux")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_UTF8)
                .expectBodyList(Integer.class)
                .hasSize(4);
    }

    @Test
    public void flux_approach3() {
        List<Integer> expectedValueList = Arrays.asList(1, 2, 3, 4);
        EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient.get().uri("/flux")
                .accept(APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();
       /* assertArrayEquals(expectedValueList.toArray(),
                entityExchangeResult.getResponseBody().toArray());
*/
        assertEquals(expectedValueList, entityExchangeResult.getResponseBody());
    }

    @Test
    public void flux_approach4() {
        List<Integer> expectedValueList = Arrays.asList(1, 2, 3, 4);
        webTestClient.get().uri("/flux")
                .accept(APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .consumeWith(result -> assertEquals(expectedValueList, result.getResponseBody()));
    }

    @Test
    public void fluxStreamTest() {
        Flux<Integer> responseBody = webTestClient
                .get()
                .uri("/fluxstream")
                .accept(MediaType.valueOf(APPLICATION_STREAM_JSON_VALUE))
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(0, 1, 2, 3, 4)
                .thenCancel()
                .verify();
    }

    @Test
    public void mono() {
        Integer expectedValue = new Integer(1);
        webTestClient.get().uri("/mono")
                .accept(APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .consumeWith(response ->
                        assertEquals(expectedValue, response.getResponseBody()));
    }


}