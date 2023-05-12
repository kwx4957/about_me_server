package com.aboutme.springwebservice.auth.kakao.client;

import com.aboutme.springwebservice.auth.kakao.model.KaKaoUser;
import com.aboutme.springwebservice.auth.kakao.model.KaKaoUserResponse;
import com.aboutme.springwebservice.auth.common.exception.ClientErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static final String HOST = "https://kapi.kakao.com";
    private static final String PROFILE = "/v2/user/me";
    private final RestTemplate template;

    public KaKaoClient() {
        this.template = new RestTemplateBuilder()
                .errorHandler(new ClientErrorHandler())
                .build();
    }

    public KaKaoUser profile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        //네이버에서 유저 정보 받아오기
        ResponseEntity<String> responseEntity = template.exchange(HOST + PROFILE, HttpMethod.POST, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        KaKaoUser profile  =null;
        //Model과 다르게 되있으면 그리고 getter setter가 없으면 오류가 날 것이다.
        try {
            profile = objectMapper.readValue(responseEntity.getBody(), KaKaoUser.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return profile;
    }
}