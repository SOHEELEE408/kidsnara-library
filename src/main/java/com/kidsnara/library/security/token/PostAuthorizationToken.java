package com.kidsnara.library.security.token;

import com.kidsnara.library.security.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 인증 후 토큰 확인
 */
public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PostAuthorizationToken(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities
    ){
        super(principal, credentials, authorities);
    }

    public static PostAuthorizationToken getTokenFormUserDetails(CustomUserDetails userDetails){
        return new PostAuthorizationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    public CustomUserDetails getUserDetails(){
        return (CustomUserDetails) super.getPrincipal();
    }
}
