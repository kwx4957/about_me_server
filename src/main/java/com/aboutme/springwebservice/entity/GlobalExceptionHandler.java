package com.aboutme.springwebservice.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handlleIllegal(IllegalArgumentException e){
        final ErrorResponse response = new ErrorResponse(e.getMessage());
        return new  ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<ErrorResponse> handlleEmpty(EmptyResultDataAccessException e){
        final ErrorResponse response = new ErrorResponse("해당하는 글 또는 댓글이 없습니다");
        return new  ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
