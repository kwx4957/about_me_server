package com.aboutme.springwebservice.login.service;

import com.aboutme.springwebservice.login.model.TokenResponse;
import jdk.nashorn.internal.parser.Token;

import java.util.Map;

public interface AppleService {

    String getAppleClientSecret(String id_token);

    TokenResponse requestCodeValidations(String client_secret, String code);

    TokenResponse requestRefreshTokenValidations(String client_secret, String refresh_token);

    Map<String, String> getLoginMetaInfo();

    String getPayload(String id_token);

}