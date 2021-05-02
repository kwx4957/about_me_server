package com.aboutme.springwebservice.login.model;

import lombok.Data;

@Data
public class SignInRequest {

    private String state;
    private String code;
    private String id_token;
    private String user;

}