package com.aboutme.springwebservice.auth.apple.model;

import lombok.Data;

@Data
public class SingInRequest {

    private String code;
    private String id_token;
    private String fcmToken;
}
