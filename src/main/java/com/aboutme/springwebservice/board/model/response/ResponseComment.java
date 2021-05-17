package com.aboutme.springwebservice.board.model.response;

import com.aboutme.springwebservice.board.model.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseComment {
    private int code;
    private String message;
    private CommentDTO comment;
}
