package com.aboutme.springwebservice.auth.apple.service;

import com.aboutme.springwebservice.auth.apple.model.TokenResponse;
import com.aboutme.springwebservice.auth.apple.model.Payload;

import java.util.Map;

public interface AppleService {

    String getAppleClientSecret(String id_token);

    TokenResponse requestCodeValidations(String client_secret, String code);

    TokenResponse requestRefreshTokenValidations(String client_secret, String refresh_token);

    Map<String, String> getLoginMetaInfo();

    Payload getPayload(String id_token);

}