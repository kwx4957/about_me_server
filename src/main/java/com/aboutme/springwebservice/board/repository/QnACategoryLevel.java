package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.board.model.response.ResponseDailyProc;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = QnACategoryLevel.setDaily_step2,
                procedureName = "aboutme_rds.setDaily2",   //실제 DB쪽 프로시저 이름
                parameters = {
                        @StoredProcedureParameter(name = "_category", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_level", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_answer", mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_share", mode = ParameterMode.IN, type = String.class),
                })
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="QnA_Category_Level")
public class QnACategoryLevel {
    public static final String setDaily_step2= "aboutme_rds.setDaily2";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    private long category_id;

    private String answer;

    private int level;

    private char share_yn;

    private int likes;

    private int scraps;

    private LocalDateTime reg_date;

    private LocalDateTime update_date;

    @Builder
    public QnACategoryLevel(long category_id, int level,String answer ,char share_yn){
        this.category_id = category_id;
        this.level = level;
        this.answer = answer;
        this.share_yn=share_yn;
    }
}
