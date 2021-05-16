package com.aboutme.springwebservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonResponse<T> extends BasicResponse{
    private int code=200;
    private T body;

    public CommonResponse(T body) {
        this.body = body;
    }

    public CommonResponse() {
        this.body= (T) "ok";
    }
}
