package com.aboutme.springwebservice.board.entity;

import com.aboutme.springwebservice.board.model.response.ResponseBoardList;
import com.aboutme.springwebservice.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = QnACategoryLevel.getLatestPost,
                procedureName = "aboutme_rds.getLatestPost",
                parameters = {
                        @StoredProcedureParameter(name = "color", mode = ParameterMode.IN, type = Integer.class),
                }
        ),
        @NamedStoredProcedureQuery(
                name = QnACategoryLevel.getPopularPost,
                procedureName = "aboutme_rds.getPopularPost",
                parameters = {}
        ),
        @NamedStoredProcedureQuery(
                name = QnACategoryLevel.getMyPopularPostList,
                procedureName = "aboutme_rds.getMyPopularPostList",
                parameters = {
                        @StoredProcedureParameter(name = "userId", mode = ParameterMode.IN, type = Integer.class),
                }
        ),
        @NamedStoredProcedureQuery(
                name = QnACategoryLevel.setDaily_step2,
                procedureName = "aboutme_rds.setDaily2",   //실제 DB쪽 프로시저 이름
                parameters = {
                        @StoredProcedureParameter(name = "_category", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_level", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_answer", mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_share", mode = ParameterMode.IN, type = String.class),
                }),
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="QnA_Category_Level")
public class QnACategoryLevel  {
    public static final String setDaily_step2 = "aboutme_rds.setDaily2";
    public static final String getLatestPost= "aboutme_rds.getLatestPost";
    public static final String getPopularPost= "aboutme_rds.getPopularPost";
    public static final String getMyPopularPostList= "aboutme_rds.getMyPopularPostList";

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
    public QnACategoryLevel(long seq, long category_id, String answer, int level, char share_yn, int likes, int scraps, LocalDateTime reg_date, LocalDateTime update_date){
        this.seq = seq;
        this.category_id = category_id;
        this.answer = answer;
        this.level = level;
        this.share_yn = share_yn;
        this.likes = likes;
        this.scraps = scraps;
        this.reg_date = reg_date;
        this.update_date = update_date;
    }

    public void addLikesCount(){
        this.likes++;
    }
    public void subtractLikes(){
        if(this.likes>0) {
            this.likes--;
        }
    }
    public void addScrapCount(){
        this.scraps++;
    }
    public void subtractScrap(){
        if(this.scraps>0) {
            this.scraps--;
        }
    }
}
