package com.aboutme.springwebservice.auth.kakao.client;

import com.aboutme.springwebservice.auth.kakao.model.KaKaoUser;
import com.aboutme.springwebservice.auth.kakao.model.KaKaoUserResponse;
import com.aboutme.springwebservice.auth.naver.model.NaverClientErrorHandler;
import com.aboutme.springwebservice.auth.naver.model.NaverUser;
import com.aboutme.springwebservice.auth.naver.model.NaverUserResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class KaKaoClient {
    private static final String HOST = "kapi.kakao.com";
    private static final String PROFILE = "/v2/user/me";
    private final RestTemplate template;

    public KaKaoClient() {
        this.template = new RestTemplateBuilder()
                .errorHandler(new NaverClientErrorHandler())
                .build();
    }

    public KaKaoUser profile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        //네이버에서 유저 정보 받아오기
        ResponseEntity<KaKaoUserResponse> responseEntity = template.exchange(HOST + PROFILE, HttpMethod.POST, entity, KaKaoUserResponse.class);

        return Objects.requireNonNull(responseEntity.getBody()).getResponse();
    }
}