package com.bong.search.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class ClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus httpStatus = HttpStatus.valueOf(response.status());
        if (httpStatus.isError()) {
            return  new ClientErrorException(methodKey, response);
        }
        return new GeneralClientException(methodKey, response);
    }
}
