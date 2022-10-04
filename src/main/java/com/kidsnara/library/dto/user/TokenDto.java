package com.kidsnara.library.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 생성된 토큰 정보
 */
@Getter @Setter
@NoArgsConstructor
public class TokenDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    public TokenDto(String token, String email){
        super();
        this.token = token;
        this.email = email;
    }
}
