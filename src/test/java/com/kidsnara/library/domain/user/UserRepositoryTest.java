package com.kidsnara.library.domain.user;

import com.kidsnara.library.constant.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository memberRepository;

    @DisplayName("유저 등록")
    @Test
    void memberInsertTest() {
        // given
        memberRepository.save(
                User.builder()
                        .username("김이름")
                        .email("test@email.com")
                        .password("1234")
                        .role(Role.USER)
                        .build()
        );

        // when
        List<User> users = memberRepository.findAll();

        // then
        assertThat(users.get(0).getUsername()).isEqualTo("김이름");
        assertThat(users.get(0).getRole()).isEqualTo(Role.USER);
    }

}