package com.aboutme.springwebservice.mypage.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseSelfQnAList {
    int user; //질답 순서
    String theme;
    int stage;
    List answerLists;

}
