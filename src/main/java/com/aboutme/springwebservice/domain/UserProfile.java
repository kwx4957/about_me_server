package com.aboutme.springwebservice.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Builder(builderMethodName = "UserProfileBuilder")
@Table(name="User_Profile")
@ToString
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
    public static UserProfileBuilder builder(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("필수 파라미터 누락");
        }
        return UserProfileBuilder().userID(id);
    }}
