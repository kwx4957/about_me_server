package com.aboutme.springwebservice.auth.common.model.response;

import lombok.Data;

@Data
public class AuthResponse {
    private final String token;
    private final Long userId;
    private final String nickName;
}
