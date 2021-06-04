package com.aboutme.springwebservice.auth.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends BaseException{
    public ResourceAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
