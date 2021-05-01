package com.aboutme.springwebservice.mypage.repository;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="User_Level")
public class UserLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(nullable = false)
    private long user_id;

    private int color;

    private int level;

    private int experience;

    @Builder
    public UserLevel(long user_id, int color, int level, int experience){
        this.user_id = user_id;
        this.color = color;
        this.level = level;
        this.experience = experience;
    }
}
