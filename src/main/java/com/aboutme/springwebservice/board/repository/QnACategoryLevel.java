package com.aboutme.springwebservice.board.repository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="QnA_Category_Level")
public class QnACategoryLevel {
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
}
