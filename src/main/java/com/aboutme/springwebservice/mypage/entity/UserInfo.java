package com.aboutme.springwebservice.mypage.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="User_Info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;
    private String email;
    private String name;
    private int gender;
    private char login_type;
    private int auth_yn;

    private LocalDateTime last_login;
    private LocalDateTime reg_date;
    private LocalDateTime update_date;

    @Builder
    public UserInfo(long seq, String email,String name ,int gender,char login_type,int auth_yn){
        this.seq=seq;
        this.email=email;
        this.name=name;
        this.gender=gender;
        this.login_type=login_type;
        this.auth_yn=auth_yn;
    }
}
