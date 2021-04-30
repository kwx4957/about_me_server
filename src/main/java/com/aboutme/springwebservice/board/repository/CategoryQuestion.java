package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.mypage.repository.SelfQuest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = CategoryQuestion.setDaily_step1,
                procedureName = "aboutme_rds.setDaily",   //실제 DB쪽 프로시저 이름
                parameters = {
                        @StoredProcedureParameter(name = "_user", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_title", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_color", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "RESULT", mode = ParameterMode.OUT, type = Integer.class)
                })
})
@Getter
@NoArgsConstructor
@Entity
public class CategoryQuestion {
    public static final String setDaily_step1= "aboutme_rds.setDaily";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(nullable = false)
    private long user;

    @Column(nullable = false)
    private int title;

    @Column(nullable = false)
    private int color;

    @Builder
    public CategoryQuestion(long seq,long user,int title, int color){
        this.seq=seq;
        this.user=user;
        this.title=title;
        this.color=color;
    }
}
