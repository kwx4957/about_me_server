package com.aboutme.springwebservice.auth.kakao.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class KaKaoUser {
    @JsonProperty("id")
    private Long userId;
    @JsonProperty("name")
    private String userName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("profile_image")
    private String profileImage;
}
