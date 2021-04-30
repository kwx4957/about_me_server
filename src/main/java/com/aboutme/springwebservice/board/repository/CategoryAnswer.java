package com.aboutme.springwebservice.board.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = CategoryAnswer.setDaily_step2,
                procedureName = "aboutme_rds.setDaily2",   //실제 DB쪽 프로시저 이름
                parameters = {
                        @StoredProcedureParameter(name = "_category", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_level", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_answer", mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_share", mode = ParameterMode.IN, type = String.class),
                })
})
@Getter
@NoArgsConstructor
@Entity
public class CategoryAnswer {
    public static final String setDaily_step2= "aboutme_rds.setDaily_step2";

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
