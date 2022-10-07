package com.kidsnara.library.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kidsnara.library.dto.user.LogInRequest;
import com.kidsnara.library.security.token.PreAuthorizationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 사용자 권한 확인 인증 필터
 */

public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;

    protected FormLoginFilter(String defaultFilterProcessesUrl){
        super(defaultFilterProcessesUrl);
    }

    public FormLoginFilter(
            AntPathRequestMatcher defaultUrl,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler
    ) {
        super(defaultUrl);
        this.authenticationSuccessHandler = successHandler;
        this.authenticationFailureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        // json으로 변환
        LogInRequest dto = new ObjectMapper().readValue(
                request.getReader(),
                LogInRequest.class
        );

        // 사용자 입력값 존재 확인
        PreAuthorizationToken token = new PreAuthorizationToken(dto);

        /*
        * PreAuthoriztionToken 객체에 맞는 Provider를
        * getAuthenticationManager() 메서드가 자동으로 찾아서 연결
        * 자동이어도 Provider에 직접 PreAuthorization을 지정해줘야 한다.
        * */
        return super
                .getAuthenticationManager()
                .authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler
                .onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        this.authenticationFailureHandler
                .onAuthenticationFailure(request, response, failed);
    }
}
