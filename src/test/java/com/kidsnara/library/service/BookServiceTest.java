package com.kidsnara.library.service;

import com.kidsnara.library.config.response.BookErrorResult;
import com.kidsnara.library.config.response.BookException;
import com.kidsnara.library.domain.library.Book;
import com.kidsnara.library.dto.book.BookRegisterReq;
import com.kidsnara.library.dto.book.BookRes;
import com.kidsnara.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository repository;

    @InjectMocks
    BookService bookService;

    private final String isbn = "9999";

    @Test
    void 도서등록실패_이미존재() {
        // given
        doReturn(Book.builder().build()).when(repository).findByIsbn(isbn);

        // when
        final BookException result = assertThrows(BookException.class, () -> bookService.saveBook(book()));

        // then
        assertThat(result.getErrorResult()).isEqualTo(BookErrorResult.DUPLICATED_BOOK_REGISTER);
    }


    @Test
    void 도서등록성공() {
        // given
        doReturn(null).when(repository).findByIsbn(isbn);
        doReturn(book()).when(repository).save(any(Book.class));

        // when
        BookRes result = bookService.saveBook(book());

        // then
        assertThat(result.getBookId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo("책 이름");

        // verify
        verify(repository, times(1)).findByIsbn(isbn);
        verify(repository, times(1)).save(any(Book.class));

    }

    private Book book() {
        return Book.builder()
                .id(-1L)
                .isbn(isbn)
                .title("책 이름")
                .author("작가명")
                .publisher("출판사")
                .price(10000)
                .count(1)
                .genre("문학")
                .build();
    }

}