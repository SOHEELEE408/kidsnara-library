package com.kidsnara.library.dto;

import com.kidsnara.library.constant.Role;
import com.kidsnara.library.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter @Setter
public class UserJoinRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private Role role = Role.USER;

    public User toEntity(){
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(this.role)
                .build();
    }
}
