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
    NO_PERMISSION(HttpStatus.UNAUTHORIZED, 2004, "권한이 없습니다."),
    DUPLICATED_USER_REGISTER(HttpStatus.BAD_REQUEST, 2005, "Duplicated User Register Request"),
    EMPTY_EMAIL(HttpStatus.BAD_REQUEST, 2006,"이메일를 입력해주세요."),
    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, 2007,"비밀번호를 입력해주세요."),
    INCORRECT_ACCOUNT(HttpStatus.BAD_REQUEST, 2008, "아이디 또는 비밀번호가 틀렸습니다."),
    DUPLICATED_BOOK_REGISTER(HttpStatus.BAD_REQUEST, 2009, "Duplicated Book Register Request"),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, 2010, "Book not found"),
    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
