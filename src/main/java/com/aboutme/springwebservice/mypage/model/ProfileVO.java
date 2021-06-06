package com.aboutme.springwebservice.mypage.model;

import lombok.Data;

@Data
public class ProfileVO {
    Long UserId;
    String nickName; // 활동닉네임 -필수
    String introduce; // 한줄소개 -필수
    Integer color; // 주 색 카테고리
    String push_yn;
    String push_time;
    Integer theme_comment;
}
