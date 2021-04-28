package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.mypage.repository.SelfQuest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = Enquiry.getDailyList,
                procedureName = "aboutme_rds.getDailyList",   //실제 DB쪽 프로시저 이름
                parameters = {
                        @StoredProcedureParameter(name = "_user", mode = ParameterMode.IN, type = Integer.class)
                })
})
@Getter
@NoArgsConstructor
@Entity
public class Enquiry {
    public static final String getDailyList = "aboutme_rds.getDailyList";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    private int type;

    @Column(nullable = false)
    private int color;

    @Column(nullable = false)
    private int theme;

    private String question;
    private int sort;

    @Builder
    public Enquiry(int seq, int type, int color,int theme,String question,int sort){
        this.seq = seq;
        this.type = type;
        this.color =color;
        this.theme =theme;
        this.question=question;
        this.sort=sort;
    }
}
