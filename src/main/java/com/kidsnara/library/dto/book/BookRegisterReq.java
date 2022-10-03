package com.kidsnara.library.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class BookRegisterReq {

    @NotNull
    private String isbn;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String publisher;

    private int price;
    private int count;

    @NotNull
    private String genre;

}
