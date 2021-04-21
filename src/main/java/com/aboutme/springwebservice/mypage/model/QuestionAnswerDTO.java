package com.aboutme.springwebservice.mypage.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class QuestionAnswerDTO {
    int user; //질답 순서
    String theme;
    String title;
    String answer;
    int stage;
    int levels;

    @Builder
    public QuestionAnswerDTO(int user,String theme,String title, String answer,int stage, int levels){
        this.user=user;
        this.title=title;
        this.answer =answer;
        this.theme =theme;
        this.stage=stage;
        this.levels=levels;
    }
    public SelfQuest toEntity() {
        return SelfQuest.builder()
                .user(user)
                .title(title)
                .answer(answer)
                .theme(theme)
                .stage(stage)
                .levels(levels)
                .build();
    }
}
