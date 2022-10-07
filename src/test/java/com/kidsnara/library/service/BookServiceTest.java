package com.kidsnara.library.service;

import com.kidsnara.library.config.exceptionhandler.BaseErrorResult;
import com.kidsnara.library.config.exceptionhandler.BaseException;
import com.kidsnara.library.domain.library.Book;
import com.kidsnara.library.dto.book.BookDetailRes;
import com.kidsnara.library.dto.book.BookGetRes;
import com.kidsnara.library.dto.book.BookRegisterRes;
import com.kidsnara.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
    private final Long id = -1L;

    @Test
    void 도서등록실패_이미존재() {
        // given
        doReturn(Book.builder().build()).when(repository).findByIsbn(isbn);

        // when
        final BaseException result = assertThrows(BaseException.class, () -> bookService.saveBook(book()));

        // then
        assertThat(result.getErrorResult()).isEqualTo(BaseErrorResult.DUPLICATED_BOOK_REGISTER);
    }


    @Test
    void 도서등록성공() {
        // given
        doReturn(null).when(repository).findByIsbn(isbn);
        doReturn(book()).when(repository).save(any(Book.class));

        // when
        BookRegisterRes result = bookService.saveBook(book());

        // then
        assertThat(result.getBookId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo("책 이름");

        // verify
        verify(repository, times(1)).findByIsbn(isbn);
        verify(repository, times(1)).save(any(Book.class));

    }

    private Book book() {
        return Book.builder()
                .id(id)
                .isbn(isbn)
                .title("책 이름")
                .author("작가명")
                .publisher("출판사")
                .price(10000)
                .count(1)
                .genre("문학")
                .build();
    }

    @Test
    void 도서목록전체조회() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "title"));
        doReturn(bookGetRes()).when(repository).findBookGetRes(pageRequest);

        // when
        final Slice<BookGetRes> result = bookService.getBookList(0);

        // then
        assertThat(result.getNumberOfElements()).isEqualTo(3);

    }

    private Slice<BookGetRes> bookGetRes(){
        return new Slice<BookGetRes>() {
            @Override
            public Iterator<BookGetRes> iterator() {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 20;
            }

            @Override
            public int getNumberOfElements() {
                return 3;
            }

            @Override
            public List<BookGetRes> getContent() {
                return Arrays.asList(
                        BookGetRes.builder().build(),
                        BookGetRes.builder().build(),
                        BookGetRes.builder().build()
                );
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public <U> Slice<U> map(Function<? super BookGetRes, ? extends U> converter) {
                return null;
            }
        };
    }

    @Test
    void 도서상세조회실패_존재하지않음() {
        // given
        doReturn(Optional.empty()).when(repository).findById(id);

        // when
        final BaseException result = assertThrows(BaseException.class, ()-> bookService.getBook(id));

        // then
        assertThat(result.getErrorResult()).isEqualTo(BaseErrorResult.BOOK_NOT_FOUND);
    }

    @Test
    void 도서상세조회성공(){
        // given
        doReturn(Optional.of(book())).when(repository).findById(id);

        // when
        final BookDetailRes result = bookService.getBook(id);

        // then
        assertThat(result.getTitle()).isEqualTo("책 이름");
    }
}