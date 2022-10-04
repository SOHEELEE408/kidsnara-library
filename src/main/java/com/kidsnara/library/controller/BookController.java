package com.kidsnara.library.controller;

import com.kidsnara.library.domain.library.Book;
import com.kidsnara.library.dto.book.BookGetRes;
import com.kidsnara.library.dto.book.BookRegisterReq;
import com.kidsnara.library.dto.book.BookRegisterRes;
import com.kidsnara.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.kidsnara.library.constant.BookConstants.ACCESS_TOKEN;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /*@GetMapping("/isbn")
    public ResponseEntity<BookDataRes> checkIsbn(@RequestParam("isbn") String isbn) throws BaseException {
        BookDataRes bookData = bookService.findBookData(isbn);
        return new ResponseEntity<>(bookData, HttpStatus.OK);
    }*/

    @PostMapping
    public ResponseEntity<BookRegisterRes> saveBook(
            @RequestHeader(ACCESS_TOKEN) final String token,
            @RequestBody @Valid final BookRegisterReq bookReq) {

        Book book = Book.builder()
                .isbn(bookReq.getIsbn())
                .title(bookReq.getTitle())
                .author(bookReq.getAuthor())
                .publisher(bookReq.getPublisher())
                .price(bookReq.getPrice())
                .count(bookReq.getCount())
                .genre(bookReq.getGenre())
                .build();

        BookRegisterRes bookRegisterRes = bookService.saveBook(book);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookRegisterRes);
    }

    @GetMapping
    public ResponseEntity<Slice<BookGetRes>> getBookList(
            @RequestHeader(ACCESS_TOKEN) final String token,
            @RequestParam(name = "page", defaultValue = "0") final int page
    ){
        return ResponseEntity.ok(bookService.getBookList(page));
    }
}
