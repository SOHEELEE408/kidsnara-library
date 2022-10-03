package com.kidsnara.library.domain.library;

import com.kidsnara.library.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
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

    private int price;

    @Column(columnDefinition = "int default 1")
    private int count;

    @Column(name = "possible_cnt")
    private int possibleCnt;

    @Column(columnDefinition = "varchar(20) default 'registered'")
    private String status;

    private String genre;

    @Builder
    public Book(Long id, String isbn, String title, String author, String publisher, int price, int count, String genre){
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.count = count;
        this.genre = genre;
    }

    @Builder
    public Book(String isbn, String title, String author, String publisher, int price, int count, String genre){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.count = count;
        this.genre = genre;
    }
}
