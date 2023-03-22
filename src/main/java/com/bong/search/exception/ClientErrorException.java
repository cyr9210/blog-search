package com.bong.search.exception;

import feign.Response;

public class ClientErrorException extends RuntimeException {
    public ClientErrorException(String methodKey, Response response) {
        super(String.format("fail: %s, message: %s", methodKey, response.body()));
    }
}
