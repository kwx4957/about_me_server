package com.aboutme.springwebservice.domain;

import com.sun.xml.bind.v2.TODO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="User_Info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column
    String email;

    @Column
    String name;

    @Column
    char gender;

    @Column
    String login_type;

    @Column
    LocalDateTime last_login;

    //TODO 관리자, 유저 판별 컬럼 
    //@Column(nullable = false)
    //@Enumerated(EnumType.STRING)
   // private Role role;

    @Column
    LocalDate reg_date;

    @Column
    LocalDateTime update_date;

    //long seq는 유저정보를 받기위한 임시
    @Builder
    public UserInfo(long seq,String email, String name, char gender, String login_type, LocalDateTime last_login, LocalDate reg_date, LocalDateTime update_date){
        this.seq=seq;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.login_type = login_type;
        this.last_login = last_login;
        this.reg_date = reg_date;
        this.update_date = update_date;
    }
}
