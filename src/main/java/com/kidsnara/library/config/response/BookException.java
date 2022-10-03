package com.kidsnara.library.config.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookException extends RuntimeException{

    private final BookErrorResult errorResult;
}
