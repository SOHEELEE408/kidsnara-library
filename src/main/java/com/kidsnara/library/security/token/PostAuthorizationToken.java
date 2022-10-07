package com.kidsnara.library.security.token;

import com.kidsnara.library.dto.user.AccountDto;
import com.kidsnara.library.security.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    public static PostAuthorizationToken getTokenFormUserDetails(AccountDto accountDto){
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(accountDto.getRole()));

        return new PostAuthorizationToken(accountDto, "null password", grantedAuthorities);
    }
}
