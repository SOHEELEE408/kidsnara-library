package com.kidsnara.library.dto.user;

import com.kidsnara.library.constant.Role;
import com.kidsnara.library.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class LogInRequest {

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식으로 입력해주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private final Role role = Role.ADMIN;

    public User toEntity() {
        return User.builder()
                .password(this.password)
                .role(this.role)
                .build();
    }
}
