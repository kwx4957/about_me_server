package com.aboutme.springwebservice.auth.service;

import com.aboutme.springwebservice.auth.model.TokenResponse;
import com.nimbusds.jose.Payload;

import java.util.Map;

public interface AppleService {

    String getAppleClientSecret(String id_token);

    TokenResponse requestCodeValidations(String client_secret, String code);

    TokenResponse requestRefreshTokenValidations(String client_secret, String refresh_token);

    Map<String, String> getLoginMetaInfo();

    Payload getPayload(String id_token);

}