package com.aboutme.springwebservice.board.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="QnA_Category")
public class QnACategory {
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
