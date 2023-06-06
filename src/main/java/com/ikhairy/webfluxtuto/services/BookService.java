package com.ikhairy.webfluxtuto.services;

import com.ikhairy.webfluxtuto.domain.Book;
import com.ikhairy.webfluxtuto.domain.BookInfo;
import com.ikhairy.webfluxtuto.domain.Review;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class BookService {
    private final BookInfoService bookService;
    private final ReviewService reviewService;

    public BookService(BookInfoService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    public Flux<Book> getBooks() {
        var books = bookService.getBooks();

        return books
                .flatMap(book -> {
                    Mono<List<Review>> reviews = reviewService.getReviews(book.getBookId()).collectList();
                    return reviews.map(review -> new Book(book, review));
                })
                .log();
    }

    public Mono<BookInfo> getBookById(long bookId) {
        BookInfo bookInfo = new BookInfo(bookId, "Book One", "Author One", "12121212");

        return Mono.just(bookInfo);
    }
}
