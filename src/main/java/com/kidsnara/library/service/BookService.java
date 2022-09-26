package com.kidsnara.library.service;

import com.kidsnara.library.repository.BookDataRepository;
import com.kidsnara.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookDataRepository dataRepository;
}
