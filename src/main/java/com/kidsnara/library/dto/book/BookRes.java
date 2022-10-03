package com.kidsnara.library.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookRes {

    private Long bookId;
    private String title;
}
