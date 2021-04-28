package com.aboutme.springwebservice.board.model;

import com.aboutme.springwebservice.board.repository.CategoryQuestion;
import com.aboutme.springwebservice.mypage.repository.SelfQuest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DailyQuestDTO {
    int user; //질답 순서
    int title;
    int color;

    @Builder
    public DailyQuestDTO( int user, int title,int color){
        this.user=user;
        this.title=title;
        this.color=color;

    }
    public CategoryQuestion toEntity() {
        return CategoryQuestion.builder()
                .user(user)
                .title(title)
                .color(color)
                .build();
    }
}
