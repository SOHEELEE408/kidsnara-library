package com.kidsnara.library.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kidsnara.library.dto.user.TokenDto;
import com.kidsnara.library.security.CustomUserDetails;
import com.kidsnara.library.security.jwt.JwtFactory;
import com.kidsnara.library.security.token.PostAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 로그인 성공했을 때 반환 정보
 */
@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtFactory factory;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        CustomUserDetails userDetails = token.getUserDetails();

        String tokenString = factory.generateToken(userDetails);

        TokenDto tokenDto = new TokenDto(tokenString, userDetails.getUsername());
        processResponse(response, tokenDto);
    }

    private void processResponse(HttpServletResponse response, TokenDto dto) throws IOException{
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(dto));
    }
}
