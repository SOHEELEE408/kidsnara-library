package com.kidsnara.library.repository;

import com.kidsnara.library.domain.library.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired BookRepository bookRepository;

    @DisplayName("도서 등록")
    @Test
    void bookRegistTest() {
        // given
        final Book book = Book.builder()
                .isbn("9999")
                .title("책 이름")
                .author("작가명")
                .publisher("출판사")
                .price(10000)
                .count(1)
                .genre("문학")
                .build();

        // when
        final Book result = bookRepository.save(book);

        // then
        assertThat(result.getId()).isNotZero();
        assertThat(result.getIsbn()).isEqualTo("9999");
    }
    
    @DisplayName("도서 존재 여부 테스트")
    @Test
    void bookOverlapTest() {
        // given
        final Book book = book();
        bookRepository.save(book);

        // when
        final Book findResult = bookRepository.findByIsbn("9999");

        // then
        assertThat(findResult).isNotNull();
        assertThat(findResult.getId()).isNotZero();
        assertThat(findResult.getTitle()).isEqualTo("책 이름");
    }

    private Book book() {
        return Book.builder()
                .isbn("9999")
                .title("책 이름")
                .author("작가명")
                .publisher("출판사")
                .price(10000)
                .count(1)
                .genre("문학")
                .build();
    }
}