package com.bong.blog.exception;

import lombok.Getter;

@Getter
public class NotSearchableException extends RuntimeException {

    private final ErrorCode errorCode;
    public NotSearchableException() {
        super("can not find blogs");
        this.errorCode = ErrorCode.CAN_NOT_CALL_CLIENT;
    }
}
