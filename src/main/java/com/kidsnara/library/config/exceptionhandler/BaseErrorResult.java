package com.kidsnara.library.config.exceptionhandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaseErrorResult implements ErrorCode{

    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, 2000,"Unknown Exception"),
    EMPTY_JWT(HttpStatus.BAD_REQUEST, 2001,"JWT를 입력해주세요."),
    INVALID_JWT(HttpStatus.BAD_REQUEST, 2002,"유효한 JWT가 아닙니다."),
    DUPLICATED_BOOK_REGISTER(HttpStatus.BAD_REQUEST, 2004, "Duplicated Book Register Request"),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, 2005, "Book not found"),
    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
