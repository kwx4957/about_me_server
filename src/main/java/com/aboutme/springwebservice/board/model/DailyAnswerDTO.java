package com.aboutme.springwebservice.board.model;

import com.aboutme.springwebservice.board.repository.CategoryAnswer;
import com.aboutme.springwebservice.board.repository.CategoryQuestion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@Setter
@NoArgsConstructor
public class DailyAnswerDTO {
    int category_seq;
    String answer;
    int level;
    String share;

    @Builder
    public DailyAnswerDTO(int category_seq, int level,String answer,String share){
        this.category_seq=category_seq;
        this.level=level;
        this.answer=answer;
        this.share=share;

    }
    public CategoryAnswer toEntity() {
        return CategoryAnswer.builder()
                .answer_seq(category_seq)
                .level(level)
                .answer(answer)
                .share(share)
                .build();
    }
}
