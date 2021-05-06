package com.aboutme.springwebservice.mypage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestWeeklyProgressing {
    private int year;
    private int month;
    private int week;
}
