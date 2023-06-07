package com.ikhairy.webfluxtuto.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoServices {
    public static final String APPLE = "Apple";
    public static final String DEFAULT = "Default";
    public static final List<String> FRUITS = List.of("Mango", "Orange", "Banana");
    public static final List<String> OTHER_FRUITS = List.of("Pineapple", "Jack Fruit");
    public static final List<String> VEGGIES = List.of("Potato", "Beans");

    public static void main(String[] args) {
        FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();

        fluxAndMonoServices.fruitsFlux()
                .subscribe(fruit -> {
                    System.out.println("Flux fruit = " + fruit);
                });

        fluxAndMonoServices.fruitsMono()
                .subscribe(fruit -> {
                    System.out.println("Mono fruit = " + fruit);
                });
    }

    public Flux<String> fruitsFlux() {
        return Flux.fromIterable(FRUITS);
    }

    public Flux<String> fruitsFluxMap() {
        return Flux.fromIterable(FRUITS)
                .map(String::toUpperCase);
    }

    public Flux<String> fruitsFluxFilter(int number) {
        return Flux.fromIterable(FRUITS)
                .filter(s -> s.length() > number);
    }

    public Flux<String> fruitsFluxFilterMap(int number) {
        return Flux.fromIterable(FRUITS)
                .filter(s -> s.length() > number)
                .map(String::toUpperCase);
    }

    public Flux<String> fruitsFluxFlatMap() {
        return Flux.fromIterable(FRUITS)
                .flatMap(s -> Flux.just(s.split("")));
    }

    public Flux<String> fruitsFluxFlatMapAsync() {
        return Flux.fromIterable(FRUITS)
                .flatMap(s -> Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(
                        new Random().nextInt(1000)
                ));
    }

    public Flux<String> fruitsFluxConcatMap() {
        return Flux.fromIterable(FRUITS)
                .concatMap(s -> Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(
                        new Random().nextInt(1000)
                ));
    }

    public Flux<String> fruitsFluxTransform(int number) {
        Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(FRUITS)
                .transform(filterData);
    }

    public Flux<String> fruitsFluxTransformDefaultIfEmpty(int number) {
        Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(FRUITS)
                .transform(filterData)
                .defaultIfEmpty(DEFAULT);
    }

    public Flux<String> fruitsFluxTransformSwitchIfEmpty(int number) {
        Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > number);
        String[] otherFruits = OTHER_FRUITS.toArray(String[]::new);

        return Flux.fromIterable(FRUITS)
                .transform(filterData)
                .switchIfEmpty(Flux.just(otherFruits)
                        .transform(filterData)
                );
    }

    public Flux<String> fruitsFluxConcat() {
        Flux<String> fruitFlux = Flux.fromIterable(FRUITS);
        Flux<String> otherFruitFlux = Flux.fromIterable(OTHER_FRUITS);

        return Flux.concat(fruitFlux, otherFruitFlux);
    }

    public Flux<String> fruitsFluxConcatWith() {
        Flux<String> fruitFlux = Flux.fromIterable(FRUITS);
        Flux<String> otherFruitFlux = Flux.fromIterable(OTHER_FRUITS);

        return fruitFlux.concatWith(otherFruitFlux);
    }

    public Flux<String> fruitsMonoConcatWith() {
        var fruit1 = Mono.just(FRUITS.get(0));
        var fruit2 = Mono.just(FRUITS.get(1));

        return fruit1.concatWith(fruit2);
    }

    public Mono<String> fruitsMono() {
        return Mono.just(APPLE);
    }

    public Mono<List<String>> fruitsMonoFlatMap() {
        return Mono.just(APPLE)
                .flatMap(s -> Mono.just(List.of(s.split(""))));
    }

    public Flux<String> fruitsMonoFlatMapMany() {
        return Mono.just(APPLE)
                .flatMapMany(s -> Flux.just(s.split("")));
    }

    public Flux<String> fruitsFluxMerge() {
        Flux<String> fruitFlux = Flux.fromIterable(FRUITS)
                .delayElements(Duration.ofMillis(50));
        Flux<String> otherFruitFlux = Flux.fromIterable(OTHER_FRUITS)
                .delayElements(Duration.ofMillis(75));;

        return Flux.merge(fruitFlux, otherFruitFlux);
    }

    public Flux<String> fruitsFluxMergeWith() {
        Flux<String> fruitFlux = Flux.fromIterable(FRUITS)
                .delayElements(Duration.ofMillis(50));
        Flux<String> otherFruitFlux = Flux.fromIterable(OTHER_FRUITS)
                .delayElements(Duration.ofMillis(75));;

        return fruitFlux.mergeWith(otherFruitFlux);
    }

    public Flux<String> fruitsFluxMergeSequential() {
        Flux<String> fruitFlux = Flux.fromIterable(FRUITS)
                .delayElements(Duration.ofMillis(50));
        Flux<String> otherFruitFlux = Flux.fromIterable(OTHER_FRUITS)
                .delayElements(Duration.ofMillis(75));;

        return Flux.mergeSequential(fruitFlux, otherFruitFlux);
    }

    public Flux<String> fruitsFluxZip() {
        Flux<String> fruitFlux = Flux.fromIterable(FRUITS)
                .delayElements(Duration.ofMillis(50));
        Flux<String> otherFruitFlux = Flux.fromIterable(OTHER_FRUITS)
                .delayElements(Duration.ofMillis(75));;

        return Flux.zip(fruitFlux, otherFruitFlux, (first, second) -> first + second);
    }

    public Flux<String> fruitsFluxZipWith() {
        Flux<String> fruitFlux = Flux.fromIterable(FRUITS)
                .delayElements(Duration.ofMillis(50));
        Flux<String> otherFruitFlux = Flux.fromIterable(OTHER_FRUITS)
                .delayElements(Duration.ofMillis(75));;

        return fruitFlux.zipWith(otherFruitFlux, (first, second) -> first + second);
    }

    public Flux<String> fruitsFluxZipTuple() {
        Flux<String> fruitFlux = Flux.fromIterable(FRUITS);
        Flux<String> otherFruitFlux = Flux.fromIterable(OTHER_FRUITS);
        Flux<String> veggiesFlux = Flux.fromIterable(VEGGIES);

        return Flux.zip(fruitFlux, otherFruitFlux, veggiesFlux)
                .map(objects -> objects.getT1() + objects.getT2() + objects.getT3());
    }

    public Mono<String> fruitsMonoZipWith() {
        var fruit1 = Mono.just(FRUITS.get(0));
        var fruit2 = Mono.just(FRUITS.get(1));

        return fruit1.zipWith(fruit2, (first, second) -> first + second);
    }

    public Flux<String> fruitsFluxFilterDoOn(int number) {
        return Flux.fromIterable(FRUITS)
                .filter(s -> s.length() > number)
                .doOnNext(System.out::println)
                .doOnSubscribe(subscription -> System.out.println(subscription.toString()))
                .doOnComplete(() -> System.out.println("Completed"));
    }

    public Flux<String> fruitsFluxOnErrorReturn() {
        String[] fruits = FRUITS.toArray(String[]::new);

        return Flux.just(fruits)
                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .onErrorReturn("Error");
    }

    public Flux<String> fruitsFluxOnErrorContinue() {
        String[] fruits = FRUITS.toArray(String[]::new);

        return Flux.just(fruits)
                .index()
                .map(object -> {
                    if (object.getT1() == 1) {
                         throw new RuntimeException("Exception occurred");
                    }

                     return object.getT2();
                })
                .onErrorContinue((throwable, o) -> {
                    System.out.println("throwable : " + throwable);
                    System.out.println("o : " + o);
                });
    }

    public Flux<String> fruitsFluxOnErrorMap() {
        String[] fruits = FRUITS.toArray(String[]::new);

        return Flux.just(fruits)
                .index()
                .checkpoint("Error Checkpoint 1")
                .map(object -> {
                    if (object.getT1() == 1) {
                        throw new RuntimeException("Exception occurred");
                    }

                    return object.getT2();
                })
                .checkpoint("Error Checkpoint 2")
                .onErrorMap(throwable -> {
                    System.out.println("throwable : " + throwable);
                    return new IllegalStateException("From onErrorMap");
                });
    }

    public Flux<String> fruitsFluxDoOnError() {
        String[] fruits = FRUITS.toArray(String[]::new);

        return Flux.just(fruits)
                .index()
                .map(object -> {
                    if (object.getT1() == 1) {
                        throw new RuntimeException("Exception occurred");
                    }

                    return object.getT2();
                })
                .doOnError(throwable -> System.out.println("From doOnError : " + throwable.getMessage()));
    }
}
