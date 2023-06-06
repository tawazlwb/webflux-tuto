package com.ikhairy.webfluxtuto.services;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.ikhairy.webfluxtuto.services.FluxAndMonoServices.*;

class FluxAndMonoServicesTest {

    FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();

    @Test
    void fruitsFlux() {
        Flux<String> fruitsFlux = fluxAndMonoServices.fruitsFlux();
        String[] fruits = FRUITS
                .stream()
                .map(String::toUpperCase)
                .toArray(String[]::new);

        StepVerifier.create(fruitsFlux)
                .expectNext(fruits)
                .verifyComplete();
    }

    @Test
    void fruitsFluxMap() {
        Flux<String> fruitsFluxMap = fluxAndMonoServices.fruitsFluxMap();
        String[] fruits = FRUITS
                .stream()
                .map(String::toUpperCase)
                .toArray(String[]::new);

        StepVerifier.create(fruitsFluxMap)
                .expectNext(fruits)
                .verifyComplete();
    }

    @Test
    void fruitsFluxFilter() {
        int length = 5;
        Flux<String> fruitsFluxFilter = fluxAndMonoServices.fruitsFluxFilter(length).log();
        String[] fruits = FRUITS
                .stream()
                .filter(s -> s.length() > length)
                .toArray(String[]::new);

        StepVerifier.create(fruitsFluxFilter)
                .expectNext(fruits)
                .verifyComplete();
    }

    @Test
    void fruitsFluxFilterMap() {int length = 5;
        Flux<String> fruitsFluxFilter = fluxAndMonoServices.fruitsFluxFilterMap(length).log();
        String[] fruits = FRUITS
                .stream()
                .filter(s -> s.length() > length)
                .map(String::toUpperCase)
                .toArray(String[]::new);

        StepVerifier.create(fruitsFluxFilter)
                .expectNext(fruits)
                .verifyComplete();

    }

    @Test
    void fruitsFluxFlatMap() {
        Flux<String> fruitsFluxMap = fluxAndMonoServices.fruitsFluxFlatMap().log();
        String[] fruits = FRUITS
                .stream()
                .flatMap(s -> Stream.of(s.split("")))
                .toArray(String[]::new);

        StepVerifier.create(fruitsFluxMap)
                .expectNext(fruits)
                .verifyComplete();

        StepVerifier.create(fruitsFluxMap)
                .expectNextCount(fruits.length)
                .verifyComplete();
    }

    @Test
    void fruitsFluxFlatMapAsync() {
        Flux<String> fruitsFluxMap = fluxAndMonoServices.fruitsFluxFlatMapAsync().log();
        String[] fruits = FRUITS
                .stream()
                .flatMap(s -> Stream.of(s.split("")))
                .toArray(String[]::new);

        StepVerifier.create(fruitsFluxMap)
                .expectNext(fruits)
                .verifyComplete();

        StepVerifier.create(fruitsFluxMap)
                .expectNextCount(fruits.length)
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcatMap() {
        Flux<String> fruitsFluxMap = fluxAndMonoServices.fruitsFluxConcatMap().log();
        String[] fruits = FRUITS
                .stream()
                .flatMap(s -> Stream.of(s.split("")))
                .toArray(String[]::new);

        StepVerifier.create(fruitsFluxMap)
                .expectNext(fruits)
                .verifyComplete();

        StepVerifier.create(fruitsFluxMap)
                .expectNextCount(fruits.length)
                .verifyComplete();
    }

    @Test
    void fruitsMono() {
        Mono<String> fruitsMono = fluxAndMonoServices.fruitsMono();
        StepVerifier.create(fruitsMono)
                .expectNext(APPLE)
                .verifyComplete();
    }

    @Test
    void fruitsMonoFlatMap() {
        Mono<List<String>> fruitsMono = fluxAndMonoServices.fruitsMonoFlatMap().log();
        List<String> list = List.of(APPLE.split(""));

        StepVerifier.create(fruitsMono)
                .expectNext(list)
                .verifyComplete();

        StepVerifier.create(fruitsMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void fruitsMonoFlatMapMany() {
        Flux<String> fruitsMonoFlatMapMany = fluxAndMonoServices.fruitsMonoFlatMapMany().log();
        String[] list = List.of(APPLE.split(""))
                .toArray(String[]::new);

        StepVerifier.create(fruitsMonoFlatMapMany)
                .expectNext(list)
                .verifyComplete();

        StepVerifier.create(fruitsMonoFlatMapMany)
                .expectNextCount(list.length)
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransform() {
        int length = 5;
        String[] fruits = FRUITS
                .stream()
                .filter(s -> s.length() > length)
                .toArray(String[]::new);

        Flux<String> fruitsFluxTransform = fluxAndMonoServices.fruitsFluxTransform(length).log();

        StepVerifier.create(fruitsFluxTransform)
                .expectNext(fruits)
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransformDefaultIfEmpty() {
        int length = 10;
        Flux<String> fruitsFluxTransform = fluxAndMonoServices.fruitsFluxTransformDefaultIfEmpty(length).log();

        StepVerifier.create(fruitsFluxTransform)
                .expectNext(DEFAULT)
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransformSwitchIfEmpty() {
        int length = 8;
        String[] otherFruits = OTHER_FRUITS
                .stream()
                .filter(s -> s.length() > length)
                .toArray(String[]::new);

        Flux<String> fruitsFluxTransform = fluxAndMonoServices.fruitsFluxTransformSwitchIfEmpty(length).log();
        StepVerifier.create(fruitsFluxTransform)
                .expectNext(otherFruits)
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcat() {
        List<String> fruits = new ArrayList<>();
        fruits.addAll(FRUITS);
        fruits.addAll(OTHER_FRUITS);

        String[] fruitsArray = fruits.toArray(String[]::new);

        Flux<String> fruitsFluxConcat = fluxAndMonoServices.fruitsFluxConcat().log();

        StepVerifier.create(fruitsFluxConcat)
                .expectNext(fruitsArray)
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcatWith() {
        List<String> fruits = new ArrayList<>();
        fruits.addAll(FRUITS);
        fruits.addAll(OTHER_FRUITS);

        String[] fruitsArray = fruits.toArray(String[]::new);

        Flux<String> fruitsFluxConcat = fluxAndMonoServices.fruitsFluxConcatWith().log();

        StepVerifier.create(fruitsFluxConcat)
                .expectNext(fruitsArray)
                .verifyComplete();
    }

    @Test
    void fruitsMonoConcatWith() {
        String[] fruitsArray = FRUITS.stream()
                .limit(2)
                .toArray(String[]::new);

        Flux<String> fruitsFluxConcat = fluxAndMonoServices.fruitsMonoConcatWith().log();

        StepVerifier.create(fruitsFluxConcat)
                .expectNext(fruitsArray)
                .verifyComplete();
    }
}