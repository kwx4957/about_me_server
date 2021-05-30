package com.aboutme.springwebservice.auth.naver.exception;

import com.aboutme.springwebservice.auth.domainModel.User;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException{
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
