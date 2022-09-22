package com.kidsnara.library.domain.library;

import com.kidsnara.library.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "isbn13")
    private String isbn;

    @Column(length = 1200)
    private String title;

    @Column(length = 1000)
    private String author;

    @Column(length = 1000)
    private String publisher;

    @Column(columnDefinition = "text", name = "img_url")
    private String imgUrl;

    @Column(length = 20)
    private String price;

    private int count;

    @Column(name = "possible_cnt")
    private int possibleCnt;

    private String status;

    @Column(name = "pub_date_2")
    private LocalDateTime publishDate;

}
