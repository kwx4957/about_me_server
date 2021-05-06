package com.aboutme.springwebservice.mypage.model.response;

import com.aboutme.springwebservice.mypage.model.ProgressingVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class ResponseProgressing {
    private int code;
    private ArrayList<ProgressingVO> data;
}
