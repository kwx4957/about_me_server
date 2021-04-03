package com.aboutme.springwebservice.mypage.model;

import lombok.Data;

@Data
public class QuestionAnswerVO {
    int questionSeq; //질답 순서
    String question;
    String answer;
}
