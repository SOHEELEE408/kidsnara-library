package com.kidsnara.library.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class BookRegisterReq {

    @NotBlank(message = "isbn을 입력하세요")
    private String isbn;

    @NotBlank(message = "도서명을 입력하세요")
    private String title;

    @NotBlank(message = "작가명을 입력하세요")
    private String author;

    @NotBlank(message = "출판사를 입력하세요")
    private String publisher;

    private int price;
    private int count;

    @NotBlank(message = "장르를 입력하세요")
    private String genre;

}
