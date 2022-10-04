package com.kidsnara.library.repository;

import com.kidsnara.library.domain.library.Book;
import com.kidsnara.library.dto.book.BookGetRes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByIsbn(String isbn);

    @Query("select new com.kidsnara.library.dto.book.BookGetRes(b.id, b.title, b.author, b.possibleCnt) from Book b ")
    Slice<BookGetRes> findBookGetRes(Pageable pageable);
}
