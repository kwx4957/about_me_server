package com.aboutme.springwebservice.mypage.model.response;

import com.aboutme.springwebservice.mypage.model.WeeklyProgressingVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWeeklyProgressing {
    private int code;
    private String message;
    private ArrayList<WeeklyProgressingVO> weeklyProgressingList;
}
