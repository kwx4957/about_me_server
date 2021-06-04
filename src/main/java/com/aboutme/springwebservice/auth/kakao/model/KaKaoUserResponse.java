package com.aboutme.springwebservice.auth.kakao.model;

import com.aboutme.springwebservice.auth.naver.model.NaverUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class KaKaoUserResponse {
    @JsonProperty("resultcode")
    private String resultCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("response")
    private KaKaoUser response;

}
