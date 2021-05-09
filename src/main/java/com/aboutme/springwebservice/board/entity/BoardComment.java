package com.aboutme.springwebservice.board.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "QnA_Comment")
public class BoardComment{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(name ="Category_level_id")
    private long categoryLevelId;
}
