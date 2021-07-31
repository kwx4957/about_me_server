package com.aboutme.springwebservice.message.model.response;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResponseNotiList {
    public String color;
    public String message;
    public String updateDate;
}
