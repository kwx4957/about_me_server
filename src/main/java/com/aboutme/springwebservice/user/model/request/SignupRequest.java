package com.aboutme.springwebservice.user.model.request;

import lombok.Data;

@Data
public class SignupRequest {
    private final String name;
    private final String email;
    private final String profileImage;
}
