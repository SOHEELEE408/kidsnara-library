package com.kidsnara.library.repository;

import com.kidsnara.library.constant.Role;
import com.kidsnara.library.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void 유저존재여부테스트() {
        // given
        final User user = User.builder()
                .username("김유저")
                .email("test3@email.com")
                .password("1234")
                .role(Role.USER)
                .build();

        userRepository.save(user);

        // when
        final boolean findResult = userRepository.existsByEmail("test3@email.com");

        // then
        assertThat(findResult).isTrue();
    }

    @DisplayName("유저 등록")
    @Test
    void memberInsertTest() {
        // given
        userRepository.save(
                User.builder()
                        .username("김이름")
                        .email("test@email.com")
                        .password("1234")
                        .role(Role.USER)
                        .build()
        );

        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users.get(0).getUsername()).isEqualTo("김이름");
        assertThat(users.get(0).getRole()).isEqualTo(Role.USER);
    }

}