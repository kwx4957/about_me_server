package com.aboutme.springwebservice.board.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseIsDailyWritten {
    int code = 200;
    String message ="ok";
    Boolean isWritten;

}
