package com.ikhairy.webfluxtuto.services;

import com.ikhairy.webfluxtuto.domain.Book;
import com.ikhairy.webfluxtuto.domain.BookInfo;
import com.ikhairy.webfluxtuto.domain.Review;
import com.ikhairy.webfluxtuto.domain.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<Book> getBookById(long bookId) {
        Mono<BookInfo> bookInfo = bookInfoService.getBookById(bookId);
        Mono<List<Review>> reviews = reviewService.getReviews(bookId).collectList();

        return Mono
                .zip(bookInfo, reviews, Book::new)
                .log();
    }
}
