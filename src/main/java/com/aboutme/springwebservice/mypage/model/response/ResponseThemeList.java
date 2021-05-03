package com.aboutme.springwebservice.mypage.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ResponseThemeList {
    int status = 200;
    String message ="ok";
    int user;
    List themeLists;

}
