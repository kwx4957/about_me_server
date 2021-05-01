package com.aboutme.springwebservice.board.repository;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = QnACategory.setDaily_step1,
                procedureName = "aboutme_rds.setDaily",   //실제 DB쪽 프로시저 이름
                parameters = {
                        @StoredProcedureParameter(name = "_user", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_title", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_color", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "RESULT", mode = ParameterMode.OUT, type = Integer.class)
                })
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="QnA_Category")
public class QnACategory {
    public static final String setDaily_step1= "aboutme_rds.setDaily";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    private long author_id;

    private long title_id;

    private int color;

    @Builder
    public QnACategory(long author_id, long title_id, int color){
        this.author_id = author_id;
        this.title_id = title_id;
        this.color = color;
    }
}
