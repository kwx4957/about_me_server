package com.aboutme.springwebservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListCommonResponse<T> extends BasicResponse{
    private int code=200;
    private List<T> body;

    public ListCommonResponse(List<T> body) {
        this.body = body;
    }
}
