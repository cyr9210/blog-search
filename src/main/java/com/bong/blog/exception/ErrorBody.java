package com.bong.blog.exception;

import lombok.Getter;

@Getter
public class ErrorBody {

    private final ErrorCode code;
    private final String message;

    public ErrorBody(ErrorCode code, String message) {
        this.code = code;
        this.message = message;
    }
}
