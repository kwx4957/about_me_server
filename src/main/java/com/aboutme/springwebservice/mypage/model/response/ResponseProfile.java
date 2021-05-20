package com.aboutme.springwebservice.mypage.model.response;

import com.aboutme.springwebservice.mypage.model.ProfileVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class ResponseProfile {
    private int code;
    private String message;
    private ProfileVO data;
}
