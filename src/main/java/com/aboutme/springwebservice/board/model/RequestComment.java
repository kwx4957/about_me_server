package com.aboutme.springwebservice.board.model;

import lombok.Data;

@Data
public class RequestComment {
    private long authorId;
    private long answerId;
    private String comment;
}
