package com.kidsnara.library.domain.user;

import com.kidsnara.library.domain.BaseTimeEntity;
import com.kidsnara.library.dto.UserCreateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String email;

    public Member(UserCreateRequest request){
        this.name = request.getName();
        this.email = request.getEmail();
    }
}
