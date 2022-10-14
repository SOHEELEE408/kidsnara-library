package com.kidsnara.library.dto.user;

import com.kidsnara.library.constant.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserJoinResponse {

    private Long userId;
    private String email;
    private Role role;
}
