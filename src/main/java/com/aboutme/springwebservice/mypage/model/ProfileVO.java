package com.aboutme.springwebservice.mypage.model;

import lombok.Data;

@Data
public class ProfileVO {
    String nickName; // 활동닉네임 -필수
    String introduce; // 한줄소개 -필수
    String color; // 주 색 카테고리

}
