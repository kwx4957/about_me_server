package com.aboutme.springwebservice.mypage.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProfileVO {
    Long UserId;
    String nickName; // 활동닉네임 -필수
    String introduce; // 한줄소개 -필수
    Integer color; // 주 색 카테고리
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Y")
    Character push_yn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "08")
    String push_time;
    Integer theme_comment;
    String gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "aaa@naver.com")
    String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate birthday;
}
