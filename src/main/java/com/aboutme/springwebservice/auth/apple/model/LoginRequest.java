package com.aboutme.springwebservice.auth.apple.model;

import lombok.Data;

@Data
public class LoginRequest {
    String userID;
    String AccessToken;
}
