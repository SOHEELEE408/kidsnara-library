package com.kidsnara.library.controller;

import com.kidsnara.library.config.exceptionhandler.BaseErrorResult;
import com.kidsnara.library.config.exceptionhandler.BaseException;
import com.kidsnara.library.config.naver.NaverClient;
import com.kidsnara.library.domain.library.Book;
import com.kidsnara.library.dto.book.*;
import com.kidsnara.library.dto.naver.SearchBookReq;
import com.kidsnara.library.dto.naver.SearchBookRes;
import com.kidsnara.library.dto.user.AccountDto;
import com.kidsnara.library.security.jwt.JwtDecoder;
import com.kidsnara.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final JwtDecoder jwtDecoder;
    private final NaverClient naverClient;

    @GetMapping("/search")
    public ResponseEntity<SearchBookRes> searchBookfromNaver(
            @RequestParam("isbn") String isbn) {
        SearchBookReq searchBookReq = new SearchBookReq();
        searchBookReq.setQuery(isbn);
        return ResponseEntity.ok(naverClient.bookSearch(searchBookReq));
    }

    @PostMapping
    public ResponseEntity<BookRegisterRes> saveBook(
            @RequestHeader(name = ACCESS_TOKEN) final String token,
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
    public ResponseEntity<Page<BookGetRes>> getBookList(
            @RequestHeader(name = ACCESS_TOKEN) final String token,
            @RequestParam(name = "page", defaultValue = "0") final int page
    ){
        return ResponseEntity.ok(bookService.getBookList(page));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDetailRes> getBook(
            @RequestHeader(name = ACCESS_TOKEN) final String token,
            @PathVariable final Long bookId
    ){
        return ResponseEntity.ok(bookService.getBook(bookId));
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<Void> updateBook(
            @RequestHeader(name = ACCESS_TOKEN) final String token,
            @RequestBody final BookPatchReq patchBookReq,
            @PathVariable final Long bookId
    ) {
        AccountDto accountDto = jwtDecoder.decodeJwt(token);
        if(!accountDto.getRole().equals("ROLE_ADMIN")){
            throw new BaseException(BaseErrorResult.NO_PERMISSION);
        }

        patchBookReq.setBookId(bookId);
        bookService.updateBook(patchBookReq);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(
            @RequestHeader(name = ACCESS_TOKEN) final String token,
            @PathVariable final Long bookId
    ){
        AccountDto accountDto = jwtDecoder.decodeJwt(token);
        if(!accountDto.getRole().equals("ROLE_ADMIN")){
            throw new BaseException(BaseErrorResult.NO_PERMISSION);
        }

        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
