package com.kidsnara.library.service;

import com.kidsnara.library.config.exceptionhandler.BaseErrorResult;
import com.kidsnara.library.config.exceptionhandler.BaseException;
import com.kidsnara.library.domain.library.Book;
import com.kidsnara.library.dto.book.BookDetailRes;
import com.kidsnara.library.dto.book.BookGetRes;
import com.kidsnara.library.dto.book.BookRegisterRes;
import com.kidsnara.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository repository;

    @Transactional
    public BookRegisterRes saveBook(Book book) {
        final Book result = repository.findByIsbn(book.getIsbn());
        if(result != null){
            throw new BaseException(BaseErrorResult.DUPLICATED_BOOK_REGISTER);
        }

        final Book savedBook = repository.save(book);

        return BookRegisterRes.builder()
                .bookId(savedBook.getId())
                .title(savedBook.getTitle())
                .build();
    }

    public Slice<BookGetRes> getBookList(int page){
        PageRequest pageRequest = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "title"));
        Slice<BookGetRes> bookGetRes = repository.findBookGetRes(pageRequest);

        return bookGetRes;
    }


    public BookDetailRes getBook(Long bookId) {
        Book book = repository.findById(bookId).orElseThrow(() -> new BaseException(BaseErrorResult.BOOK_NOT_FOUND));

        return BookDetailRes.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .price(book.getPrice())
                .count(book.getCount())
                .count(book.getPossibleCnt())
                .genre(book.getGenre())
                .build();
    }
}
