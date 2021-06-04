package com.aboutme.springwebservice.auth.apple.model;

import lombok.Data;

@Data
public class TokenResponse {

    private String access_token;
    private Long expires_in;
    private String id_token;
    private String refresh_token;
    private String token_type;

}