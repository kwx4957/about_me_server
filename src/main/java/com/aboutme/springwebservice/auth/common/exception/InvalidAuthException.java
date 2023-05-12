package com.aboutme.springwebservice.auth.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidAuthException extends BaseException{
    public InvalidAuthException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
