package com.aboutme.springwebservice.auth.naver.client;

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
public class NaverClient {
    private static final String HOST = "https://openapi.naver.com";
    private static final String PROFILE = "/v1/nid/me";
    private final RestTemplate template;

    public NaverClient() {
        this.template = new RestTemplateBuilder()
                .errorHandler(new NaverClientErrorHandler())
                .build();
    }

    public NaverUser profile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        //네이버에서 유저 정보 받아오기
        ResponseEntity<NaverUserResponse> responseEntity = template.exchange(HOST + PROFILE, HttpMethod.GET, entity, NaverUserResponse.class);

        return Objects.requireNonNull(responseEntity.getBody()).getResponse();
    }
}