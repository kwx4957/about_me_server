package com.aboutme.springwebservice.mypage.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseMyMain {
    int code =200;
    String message ="ok";
    int user_id=0;
    String nickName=""; // 활동닉네임 -필수
    String introduce=""; // 한줄소개 -필수
    String color=""; // 주 색 카테고리
    String color_tag=""; // 색 별칭
    List postList; // 내가쓴글
}
