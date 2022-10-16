package com.kidsnara.library.security.provider;

import com.kidsnara.library.config.exceptionhandler.BaseErrorResult;
import com.kidsnara.library.config.exceptionhandler.BaseException;
import com.kidsnara.library.service.UserServiceImpl;
import com.kidsnara.library.security.CustomUserDetails;
import com.kidsnara.library.security.token.PostAuthorizationToken;
import com.kidsnara.library.security.token.PreAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/**
 * 로그인 한 사용자의 인증 권한 검사
 */
@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final UserServiceImpl userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String email = token.getEmail();
        String password = token.getUserPassword();

        CustomUserDetails userDetails = userService.loadUserByUsername(email);

        if(isCorrectPassword(password, userDetails.getPassword())){
            return PostAuthorizationToken
                    .getTokenFormUserDetails(userDetails);
        }

        throw new BaseException(BaseErrorResult.INCORRECT_ACCOUNT);
    }

    /*
    * Provider를 연결해 주는 메서드
    * PreAuthorizationToken을 사용한 filter를 검색 후 연결
    * */
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean isCorrectPassword(String password, String accountPassword){
        return passwordEncoder.matches(password, accountPassword);
    }
}
