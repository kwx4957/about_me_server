package com.aboutme.springwebservice.board.entity;

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

@Getter
@NoArgsConstructor
@Entity
@Table(name="QnA_Category")
public class QnACategory {
    public static final String setDaily_step1= "aboutme_rds.setDaily";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(name ="author_id")
    private long authorId;

    @Column(name ="title_id")
    private long titleId;

    private int color;

    @Builder
    public QnACategory(long author_id, long title_id, int color){
        this.authorId = author_id;
        this.titleId = title_id;
        this.color = color;
    }
}
