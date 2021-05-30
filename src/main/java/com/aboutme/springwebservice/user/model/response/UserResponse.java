package com.aboutme.springwebservice.user.model.response;

import lombok.Data;
import lombok.Getter;

@Data
public class UserResponse {
    private final long id;
    private final String name;
    private final String email;
    private final String profileImage;
}
