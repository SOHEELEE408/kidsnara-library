package com.kidsnara.library.dto.book;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookDetailRes {

    private String isbn;

    private String title;

    private String author;

    private String publisher;

    private int price;

    private int count;

    private int possibleCnt;

    private String genre;
}
