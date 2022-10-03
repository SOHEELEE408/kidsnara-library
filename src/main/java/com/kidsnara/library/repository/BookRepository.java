package com.kidsnara.library.repository;

import com.kidsnara.library.domain.library.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByIsbn(String isbn);
}
