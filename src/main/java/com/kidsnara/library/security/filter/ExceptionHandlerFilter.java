package com.kidsnara.library.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kidsnara.library.config.exceptionhandler.BaseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch(BaseException exception){
            log.warn("BaseException occur : ", exception);
            setErrorResponse(HttpStatus.BAD_REQUEST, response, exception);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, BaseException exception){
        response.setStatus(status.value());
        response.setContentType("application/json; charset=utf-8");
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorResult().getCode(), exception.getErrorResult().getMessage());
        try{
            String json = mapper.writeValueAsString(errorResponse);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Getter
    @RequiredArgsConstructor
    static class ErrorResponse{
        private final int code;
        private final String message;
    }
}
