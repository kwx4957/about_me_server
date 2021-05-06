package com.aboutme.springwebservice.mypage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeeklyProgressingVO {
    private String color;
    private String regDate;
    private String day;
    private Boolean isWritten;
}
