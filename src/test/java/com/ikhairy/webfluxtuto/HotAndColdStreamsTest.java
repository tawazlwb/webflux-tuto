package com.ikhairy.webfluxtuto;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

@ExtendWith(MockitoExtension.class)
public class HotAndColdStreamsTest {

    @Test
    void coldStreamsTest() {
        Flux<Integer> numbers = Flux.range(1, 10);

        numbers.subscribe(integer -> System.out.println("subscriber 1 = " + integer));
        numbers.subscribe(integer -> System.out.println("subscriber 2 = " + integer));
    }

    @SneakyThrows
    @Test
    void hotStreamsTest() {
        Flux<Integer> numbers = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(1000));

        ConnectableFlux<Integer> publisher = numbers.publish();
        publisher.connect();

        publisher.subscribe(integer -> System.out.println("subscriber 1 = " + integer));

        Thread.sleep(4000);

        publisher.subscribe(integer -> System.out.println("subscriber 2 = " + integer));

        Thread.sleep(10000);
    }
}
