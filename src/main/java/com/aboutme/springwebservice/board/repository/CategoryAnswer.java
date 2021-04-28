package com.aboutme.springwebservice.board.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class CategoryAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(nullable = false)
    private long answer_seq;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private int level;

    private String share;
    private long likeCount;
    private long scrapCount;

    @Builder
    public CategoryAnswer(long seq,long answer_seq,String answer,int level,
                          String share,int likeCount, int scrapCount){
        this.seq=seq;
        this.answer_seq =answer_seq;
        this.answer = answer;
        this.level=level;
        this.share=share;
        this.likeCount=likeCount;
        this.scrapCount=scrapCount;
    }
}
