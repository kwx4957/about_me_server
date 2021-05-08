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
//        if(body instanceof List) {
//            this.count = ((List<?>)body).size();
//        } else {
//            this.count = 1;
//        }
    }
}
