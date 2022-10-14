package com.kidsnara.library.controller;

import com.google.gson.Gson;
import com.kidsnara.library.config.exceptionhandler.GlobalExceptionHandler;
import com.kidsnara.library.constant.Role;
import com.kidsnara.library.domain.user.User;
import com.kidsnara.library.dto.user.UserJoinRequest;
import com.kidsnara.library.dto.user.UserJoinResponse;
import com.kidsnara.library.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    void init(){
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @ParameterizedTest
    @MethodSource("invalidUserAddParameter")
    void 회원가입실패_잘못된파라미터(String username, String email, String password, String role) throws Exception{
        // given
        final String url = "/users/join";

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(userJoinRequest(username, email, password, role)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    private UserJoinRequest userJoinRequest(String username, String email, String password, String role){
        return new UserJoinRequest(username, email, password, role);
    }

    private static Stream<Arguments> invalidUserAddParameter(){
        return Stream.of(
                Arguments.of(null, "test222@email.com", "1234", "USER"),
                Arguments.of("김유저", null, "1234", "USER"),
                Arguments.of("김유저", "test222@email.com", null, "USER"),
                Arguments.of("김유저", "test222@email.com", "1234", null)
        );
    }

    @Test
    void 유저등록성공() throws Exception {
        // given
        final String url = "/users/join";
        final UserJoinResponse response = UserJoinResponse.builder()
                        .userId(-1L)
                        .email("test222@email.com")
                        .role(Role.USER)
                        .build();

        doReturn(response).when(userService).saveOrUpdateUser(any(User.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(userJoinRequest("김유저", "test222@email.com", "1234", "USER")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isCreated());

        UserJoinResponse res = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), UserJoinResponse.class);

        assertThat(res.getEmail()).isEqualTo("test222@email.com");
        assertThat(res.getUserId()).isNotZero();
    }
}