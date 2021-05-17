package com.aboutme.springwebservice.board.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePost
{
    private int code;
    private String message;
    private Object post;
    private List comments;
}
