package com.kidsnara.library.security.provider;

import com.kidsnara.library.dto.user.AccountDto;
import com.kidsnara.library.security.jwt.JwtDecoder;
import com.kidsnara.library.security.token.JwtPreProcessingToken;
import com.kidsnara.library.security.token.PostAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationProvider implements AuthenticationProvider {

    private final JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        AccountDto accountDto = jwtDecoder.decodeJwt(token);

        return PostAuthorizationToken.getTokenFormUserDetails(accountDto);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreProcessingToken.class.isAssignableFrom(authentication);
    }
}
