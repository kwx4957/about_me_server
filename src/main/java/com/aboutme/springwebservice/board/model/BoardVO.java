package com.aboutme.springwebservice.board.model;

import lombok.Data;

@Data
public class BoardVO {
    String userName;
    String question;
    String answer;
    int likeCount;
    int scrapCount;
    String color;
}
