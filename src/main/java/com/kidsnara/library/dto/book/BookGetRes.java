package com.kidsnara.library.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookGetRes {

    private Long bookId;
    private String title;
    private String author;
    private int possibleCnt;
}
