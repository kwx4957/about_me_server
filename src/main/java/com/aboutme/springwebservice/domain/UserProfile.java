package com.aboutme.springwebservice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="User_Profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(name = "user_id")
    private long userID;

    @Column(name = "introduce")
    private String intro;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "color")
    private Integer color;

    @Column(name = "theme_comment")
    private Integer themeComment;

    @Column(name = "push_yn")
    private char push_yn;

    @Column(name = "reg_date")
    private LocalDateTime reg_date;

    @Column(name = "update_time")
    private LocalDateTime update_date;

    @Builder
    public UserProfile(long seq, int userID,String intro,String nickname,int color,char push_yn){
        this.seq=seq;
        this.userID=userID;
        this.intro=intro;
        this.nickname=nickname;
        this.color=color;
        this.push_yn=push_yn;
    }
}
