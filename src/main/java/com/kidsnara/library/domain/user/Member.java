package com.kidsnara.library.domain.user;

import com.kidsnara.library.domain.BaseEntity;
import com.kidsnara.library.domain.library.Borrow;
import com.kidsnara.library.dto.UserCreateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "member")
    private List<Borrow> borrows = new ArrayList<>();

    public void addLend(Borrow borrow) {
        borrow.setMember(this);
        borrows.add(borrow);
    }

    public Member(UserCreateRequest request){
        this.name = request.getName();
        this.email = request.getEmail();
    }
}
