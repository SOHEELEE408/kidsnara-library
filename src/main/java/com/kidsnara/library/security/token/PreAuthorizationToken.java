package com.kidsnara.library.security.token;

import com.kidsnara.library.dto.LogInRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 로그인 한 사용자의 권한 확인을 위한 Pre Token 생성
 */
public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PreAuthorizationToken(String email, String password){
        super(email, password);
    }

    public PreAuthorizationToken(LogInRequest dto){
        this(dto.getEmail(), dto.getPassword());
    }

    public String getEmail(){
        return (String) super.getPrincipal();
    }

    public String getUserPassword(){
        return (String) super.getCredentials();
    }
}
