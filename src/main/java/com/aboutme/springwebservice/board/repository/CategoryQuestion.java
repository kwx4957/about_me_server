package com.aboutme.springwebservice.board.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class CategoryQuestion {

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
