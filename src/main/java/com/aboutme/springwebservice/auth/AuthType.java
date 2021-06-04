package com.aboutme.springwebservice.auth;

import lombok.Getter;

@Getter
public enum AuthType {
    Naver("네이버"),
    Kakao("카카오"),
    Apple("애플");

    private String description;
    AuthType(String description) {
        this.description = description;
    }

}
