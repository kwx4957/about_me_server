package com.aboutme.springwebservice.auth.naver.exception;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends BaseException{
    public ResourceAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
