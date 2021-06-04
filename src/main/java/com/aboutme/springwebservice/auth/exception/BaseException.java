package com.aboutme.springwebservice.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException{
    private final String message;
    private final HttpStatus httpStatus;
}
