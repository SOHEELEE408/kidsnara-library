package com.kidsnara.library.domain.library;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookData {

    @Id
    @Column(name = "master_seq")
    private Long id;

    @Column(name = "isbn13")
    private String isbn;

    @Column(name = "isbn_origin")
    private String originalIsbn;

    private String title;
    private String author;
    private String publisher;
    private int price;

    @Column(columnDefinition = "text", name = "img_url")
    private String imgUrl;

    @Column(name = "pub_date_2")
    private LocalDateTime publishDate;
}
