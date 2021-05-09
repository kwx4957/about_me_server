package com.aboutme.springwebservice.board.entity;

import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "QnA_Like_Info")
public class BoardInteraction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(name = "like_yn" ,nullable = true)
    private char likeYn;

    @Column(name = "scrap_yn" ,nullable = true)
    private char scrapYn;

    @ManyToOne(targetEntity = QnACategoryLevel.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "category_level_id",nullable = false)
    private QnACategoryLevel board;

    @ManyToOne(targetEntity = UserInfo.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "like_user", nullable = false)
    private UserInfo likeUser;

    @ManyToOne(targetEntity = UserInfo.class,fetch = FetchType.LAZY) //casecade 공부후 적용?
    @JoinColumn(name = "author_id", nullable = false)
    private UserInfo authorId;

    @Builder
    public BoardInteraction(QnACategoryLevel board, UserInfo likeUser, char likeYn, UserInfo authorId){
        this.board=board;
        this.likeUser=likeUser;
        this.likeYn=likeYn;
        this.authorId=authorId;
    }

    public void likeYes(){
        this.likeYn='Y';
    }
    public void likeNo(){
        this.likeYn='N';
    }
    public void scrapYes(){
        this.scrapYn='Y';
    }
    public void scrapNo(){
        this.scrapYn='N';
    }

}


