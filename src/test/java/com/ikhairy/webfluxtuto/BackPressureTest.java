package com.ikhairy.webfluxtuto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
public class BackPressureTest {

    @Test
    void testBackPressure() {
        Flux<Integer> numbers = Flux.range(1, 100).log();
        //numbers.subscribe(integer -> System.out.println("integer = " + integer));

        numbers.subscribe(new BaseSubscriber<Integer>() {
            private int count = 0;

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(3);
            }

            @Override
            protected void hookOnNext(Integer value) {
                ++count;
                System.out.println("value = " + value);

                if (count == 3) {
                    cancel();
                }
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("Completed!");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                super.hookOnCancel();
            }
        });
    }

    @Test
    void testBackPressureDrop() {
        Flux<Integer> numbers = Flux.range(1, 100).log();
        numbers
                .onBackpressureDrop(integer -> {
                    System.out.println("Dropped value = " + integer);
                })
                .subscribe(new BaseSubscriber<Integer>() {
                    private int count = 0;

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(3);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        ++count;
                        System.out.println("value = " + value);

                        if (count == 3) {
                            hookOnCancel();
                        }
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("Completed!");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        super.hookOnError(throwable);
                    }

                    @Override
                    protected void hookOnCancel() {
                        super.hookOnCancel();
                    }
                });
    }
}
