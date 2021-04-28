package com.aboutme.springwebservice.board.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseDailyLists{
    int user;
    List dailyLists; // 질문 일련번호 - 질문 - (답) - 색상 - 레벨 정보 담음
}
