package com.aboutme.springwebservice.board.model;

import lombok.Data;

@Data
public class BoardVO {
    int level;
    int user; //질답 순서
    int title;
    String answer;
    String color;
    String share_yn;

}
