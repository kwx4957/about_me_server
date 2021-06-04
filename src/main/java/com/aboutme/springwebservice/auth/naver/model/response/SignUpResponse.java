package com.aboutme.springwebservice.auth.naver.model.response;

import lombok.Data;

@Data
public class SignUpResponse {
    private final String token;
    private final Long UserId;
    private final String email;
    private final String profile;
}
