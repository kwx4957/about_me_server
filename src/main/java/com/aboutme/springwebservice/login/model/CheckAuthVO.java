package com.aboutme.springwebservice.login.model;

import lombok.Data;

@Data
public class CheckAuthVO {
    String accessToken;
    String userId;
}
