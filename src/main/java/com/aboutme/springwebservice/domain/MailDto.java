package com.aboutme.springwebservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private int reasonNum;
    private int userId;
    private String message;
}