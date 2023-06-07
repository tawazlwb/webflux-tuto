package com.ikhairy.webfluxtuto.services;

import com.ikhairy.webfluxtuto.domain.Book;
import com.ikhairy.webfluxtuto.domain.BookInfo;
import com.ikhairy.webfluxtuto.domain.Review;
import com.ikhairy.webfluxtuto.domain.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.util.List;

@Slf4j
public class BookService {
    private final BookInfoService bookInfoService;
    private final ReviewService reviewService;

    public BookService(BookInfoService bookInfoService, ReviewService reviewService) {
        this.bookInfoService = bookInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Book> getBooks() {
        var books = bookInfoService.getBooks();

        return books
                .flatMap(book -> {
                    Mono<List<Review>> reviews = reviewService.getReviews(book.getBookId()).collectList();
                    return reviews.map(review -> new Book(book, review));
                })
                .onErrorMap(throwable -> {
                    log.error("Exception occurred : ", throwable);
                    return new BookException("Exception occurred while fetching Books");
                })
                .log();
    }

    public Flux<Book> getBooksRetry() {
        var books = getBooks();

        return books.retry(3);
    }

    public Flux<Book> getBooksRetryWhen() {
        RetryBackoffSpec retrySpec = getRetryBackoffSpec();
        var books = getBooks();

        return books.retryWhen(retrySpec);
    }

    private RetryBackoffSpec getRetryBackoffSpec() {
        return Retry
                .backoff(3, Duration.ofMillis(100))
                .filter(throwable -> throwable instanceof BookException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> Exceptions.propagate(retrySignal.failure()));
    }

    public Mono<Book> getBookById(long bookId) {
        Mono<BookInfo> bookInfo = bookInfoService.getBookById(bookId);
        Mono<List<Review>> reviews = reviewService.getReviews(bookId).collectList();

        return Mono
                .zip(bookInfo, reviews, Book::new)
                .log();
    }
}
