package com.aboutme.springwebservice.board.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
@Table(name = "QnA_Comment")
public class BoardComment{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(name ="category_level_id")
    private long categoryLevelId;

    @Column(name = "author_id")
    private long authorId;

    @Column(name = "user_comment")
    private String comment;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Builder BoardComment(long seq, long categoryLevelId, long authorId, String comment, LocalDateTime regDate){
        this.seq = seq;
        this.categoryLevelId = categoryLevelId;
        this.authorId = authorId;
        this.comment = comment;
        this.regDate = regDate;
    }
}
