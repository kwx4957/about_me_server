package com.aboutme.springwebservice.mypage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DayColor {
    private String color;
    private String day;
    private Boolean isWritten;
}
