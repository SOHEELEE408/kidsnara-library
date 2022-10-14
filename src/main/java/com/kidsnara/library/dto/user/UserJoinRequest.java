package com.kidsnara.library.dto.user;

import com.kidsnara.library.constant.Role;
import com.kidsnara.library.domain.user.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {

    @NotNull(message = "이름을 입력해주세요.")
    private String username;

    @NotNull(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotNull(message = "권한을 선택해주세요.")
    private String role;

    @Builder
    public User toEntity(){
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(Role.valueOf(role))
                .build();
    }
}
