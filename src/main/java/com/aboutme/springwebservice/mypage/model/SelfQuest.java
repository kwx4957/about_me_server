package com.aboutme.springwebservice.mypage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = SelfQuest.set10Q10A,
                procedureName = "aboutme_rds.set10Q10A",   //실제 DB쪽 프로시저 이름
                parameters = {
                        @StoredProcedureParameter(name = "_user",mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_title",mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_answer",mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_theme",mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_stages",mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_levels",mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "RESULT",mode = ParameterMode.OUT, type = String.class)
                }),
        @NamedStoredProcedureQuery(
                name = SelfQuest.update10Q10A,
                procedureName = "aboutme_rds.update10Q10A",   //실제 DB쪽 프로시저 이름
                parameters = {
                        @StoredProcedureParameter(name = "_user",mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_title",mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_answer",mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_theme",mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_stages",mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_levels",mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "RESULT",mode = ParameterMode.OUT, type = String.class)
                }),
        @NamedStoredProcedureQuery(
                name = SelfQuest.update10Q10A,
                procedureName = "aboutme_rds.update10Q10A",   //실제 DB쪽 프로시저 이름
                parameters = {
                        @StoredProcedureParameter(name = "_user",mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_title",mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_answer",mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_theme",mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_stages",mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_levels",mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "RESULT",mode = ParameterMode.OUT, type = String.class)
                })
})
@Getter
@NoArgsConstructor
@Entity
public class SelfQuest {
    public static final String set10Q10A = "aboutme_rds.set10Q10A";
    public static final String update10Q10A = "aboutme_rds.update10Q10A";
    public static final String get10Q10A = "aboutme_rds.get10Q10A";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user;

    @Column(nullable = false)
    private String theme;

    @Column(nullable = false)
    private String title;

    private String answer;

    private long stage;
    private long levels;

    @Builder
    public SelfQuest(int user,String theme,String title, String answer,int stage, int levels){
        this.user=user;
        this.title=title;
        this.answer =answer;
        this.theme =theme;
        this.stage=stage;
        this.levels=levels;
    }

   // private Timestamp regdate;
}
