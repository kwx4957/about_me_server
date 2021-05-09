package com.aboutme.springwebservice.board.model;

import com.aboutme.springwebservice.board.repository.QnACategory;
import com.aboutme.springwebservice.board.repository.QnACategoryLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DailyAnswerDTO {
    int category_seq;
    String answer;
    int level;
    char share;

    @Builder
    public DailyAnswerDTO(int category_seq, int level,String answer,char share){
        this.category_seq=category_seq;
        this.level=level;
        this.answer=answer;
        this.share=share;

    }
    public QnACategoryLevel toEntity() {
        return QnACategoryLevel.builder()
                .category_id(category_seq)
                .level(level)
                .answer(answer)
                .share_yn(share)
                .build();
    }
}
