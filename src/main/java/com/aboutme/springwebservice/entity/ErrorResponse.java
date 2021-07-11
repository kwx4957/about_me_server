package com.aboutme.springwebservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse extends BasicResponse {
    private String errorMessage;
    private String errorCode;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = "404";
    }
    public ErrorResponse(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.errorCode = httpStatus.toString().split(" ")[0];
    }

    public ErrorResponse(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}



