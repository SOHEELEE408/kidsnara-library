package com.kidsnara.library.domain.library;

import com.kidsnara.library.domain.BaseEntity;
import com.kidsnara.library.domain.user.Member;

import javax.persistence.*;

@Entity
public class Borrow extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public void changeBook(Book book){ // book이랑 lend 사이에 양방향을 해야할까?
        this.book = book;
        book.getBorrows().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
