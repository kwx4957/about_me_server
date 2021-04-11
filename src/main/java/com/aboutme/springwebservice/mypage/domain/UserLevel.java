package com.aboutme.springwebservice.mypage.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class UserLevel {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private long user_id;

    @Column
    private int color;

    @Column
    private int level;

    @Column
    private int experience;
}
