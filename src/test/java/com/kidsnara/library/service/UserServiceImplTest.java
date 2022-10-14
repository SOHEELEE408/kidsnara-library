package com.kidsnara.library.service;

import com.kidsnara.library.config.exceptionhandler.BaseErrorResult;
import com.kidsnara.library.config.exceptionhandler.BaseException;
import com.kidsnara.library.constant.Role;
import com.kidsnara.library.domain.user.User;
import com.kidsnara.library.dto.user.UserJoinResponse;
import com.kidsnara.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Spy
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void 회원가입실패_이메일중복() {
        // given
        doReturn(true).when(userRepository).existsByEmail(anyString());

        // when
        final BaseException result = assertThrows(BaseException.class, () -> userService.saveOrUpdateUser(user()));

        // then
        assertThat(result.getErrorResult()).isEqualTo(BaseErrorResult.DUPLICATED_USER_REGISTER);

    }


    @Test
    void 회원가입성공() {
        // given
        doReturn(false).when(userRepository).existsByEmail(anyString());
        doReturn(user()).when(userRepository).save(any(User.class));

        // when
        UserJoinResponse result = userService.saveOrUpdateUser(user());

        // then
        assertThat(result.getEmail()).isEqualTo("test333@email.com");

    }

    private User user(){
        return new User(-1L, "김이름", "test333@email.com", "1234", Role.USER);
    }

}