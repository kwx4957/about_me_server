package com.aboutme.springwebservice.auth.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException{
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
