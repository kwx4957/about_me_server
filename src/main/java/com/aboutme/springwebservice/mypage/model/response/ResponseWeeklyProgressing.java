package com.aboutme.springwebservice.mypage.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWeeklyProgressing {
    private int color;
    private String reg_date;
    private String day;
}
