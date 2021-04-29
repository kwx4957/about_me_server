package com.aboutme.springwebservice.mypage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class ProgressingVO {
    char color;
    int level;
    int experience;
}
