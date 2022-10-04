package com.kidsnara.library.config.exceptionhandler;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

    private final BaseErrorResult errorResult;

    public BaseException(BaseErrorResult errorResult){
        super(errorResult.name());
        this.errorResult = errorResult;
    }
}
