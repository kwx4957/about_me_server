package com.aboutme.springwebservice.auth.model;

import lombok.Data;

@Data
public class LoginRequest {
    String userID;
    String AccessToken;
}
