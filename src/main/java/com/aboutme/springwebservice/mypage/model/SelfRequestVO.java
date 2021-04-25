package com.aboutme.springwebservice.mypage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SelfRequestVO {
    int user; //질답 순서
    String theme;
    int stage;
    List<AnswerList> answerLists;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AnswerList {
        private int level;
        private String question;
        private String answer;
    }
}
