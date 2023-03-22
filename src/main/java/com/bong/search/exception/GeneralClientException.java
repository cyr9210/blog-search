package com.bong.search.exception;

import feign.Response;

public class GeneralClientException extends RuntimeException {

    public GeneralClientException(String methodKey, Response response) {
        super(String.format("fail: %s, message: %s", methodKey, response.body()));
    }
}
