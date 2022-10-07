package com.kidsnara.library.config.exceptionhandler;


import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String name();
    HttpStatus getHttpStatus();
    int getCode();
    String getMessage();
}
