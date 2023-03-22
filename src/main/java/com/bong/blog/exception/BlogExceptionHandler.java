package com.bong.blog.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BlogExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotSearchableException.class)
    public ResponseEntity<Object> canNotSearchBlogs(NotSearchableException ex, WebRequest request) {
        ErrorBody errorBody = new ErrorBody(ex.getErrorCode(), ex.getMessage());
        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
