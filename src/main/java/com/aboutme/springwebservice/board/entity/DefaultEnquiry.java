package com.aboutme.springwebservice.board.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="Default_Enquiry")
public class DefaultEnquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    private int type;

    private int color;

    private int theme;

    private String question;

    private int sort;

    private LocalDateTime reg_date;
}
