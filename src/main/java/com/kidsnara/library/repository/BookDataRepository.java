package com.kidsnara.library.repository;

import com.kidsnara.library.domain.library.BookData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDataRepository extends JpaRepository<BookData, Long> {
}
