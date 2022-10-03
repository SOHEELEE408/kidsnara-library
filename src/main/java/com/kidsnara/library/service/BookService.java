package com.kidsnara.library.service;

import com.kidsnara.library.config.response.BookErrorResult;
import com.kidsnara.library.config.response.BookException;
import com.kidsnara.library.domain.library.Book;
import com.kidsnara.library.dto.book.BookRes;
import com.kidsnara.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository repository;

    public BookRes saveBook(Book book) {
        final Book result = repository.findByIsbn(book.getIsbn());
        if(result != null){
            throw new BookException(BookErrorResult.DUPLICATED_BOOK_REGISTER);
        }

        final Book savedBook = repository.save(book);

        return BookRes.builder()
                .bookId(savedBook.getId())
                .title(savedBook.getTitle())
                .build();
    }
}
