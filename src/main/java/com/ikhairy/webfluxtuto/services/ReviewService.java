package com.ikhairy.webfluxtuto.services;

import com.ikhairy.webfluxtuto.domain.Review;
import reactor.core.publisher.Flux;

import java.util.List;

public class ReviewService {
    public Flux<Review> getReviews(long bookId) {
        var reviews = List.of(
                new Review(1,bookId,9.1,"Good Book"),
                new Review(2,bookId,8.6,"Worth Reading")
        );

        return Flux.fromIterable(reviews);
    }
}
