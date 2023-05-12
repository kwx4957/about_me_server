package com.aboutme.springwebservice.board.model;

import com.aboutme.springwebservice.board.entity.QnACategory;
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
    public QnACategory toEntity() {
        return QnACategory.builder()
                .author_id(user)
                .title_id(title)
                .color(color)
                .build();
    }
}
