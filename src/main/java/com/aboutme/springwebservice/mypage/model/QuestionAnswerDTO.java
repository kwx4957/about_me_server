package com.aboutme.springwebservice.mypage.model;

import com.aboutme.springwebservice.mypage.entity.SelfQuest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class QuestionAnswerDTO {
    int user; //질답 순서
    String theme;
    String theme_new;
    String title;
    String answer;
    int stage;
    int levels;

    @Builder
    public QuestionAnswerDTO(int user,String theme,String theme_new,String title, String answer,int stage, int levels){
        this.user=user;
        this.title=title;
        this.answer =answer;
        this.theme =theme;
        this.theme_new=theme_new;
        this.stage=stage;
        this.levels=levels;
    }
}
