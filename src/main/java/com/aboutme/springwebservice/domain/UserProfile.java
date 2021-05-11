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
    @Column
    private int userId;
    private String intro;
    private String nickname;
    private int color;
    private char push_yn;

    private LocalDateTime reg_date;
    private LocalDateTime update_date;

    @Builder
    public UserProfile(long seq, int userId,String intro,String nickname,int color,char push_yn){
        this.seq=seq;
        this.userId=userId;
        this.intro=intro;
        this.nickname=nickname;
        this.color=color;
        this.push_yn=push_yn;
    }
}
