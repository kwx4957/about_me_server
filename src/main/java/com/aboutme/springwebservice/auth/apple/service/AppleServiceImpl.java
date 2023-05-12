package com.aboutme.springwebservice.auth.apple.service;

import com.aboutme.springwebservice.auth.apple.model.TokenResponse;
import com.aboutme.springwebservice.auth.apple.model.Payload;
import com.aboutme.springwebservice.auth.apple.utils.AppleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppleServiceImpl implements AppleService {

    @Autowired
    AppleUtils appleUtils;

    /**
     * 유효한 id_token인 경우 client_secret 생성
     *
     * @param id_token
     * @return
     */
    @Override
    public String getAppleClientSecret(String id_token) {

        if (appleUtils.verifyIdentityToken(id_token)) {
            return appleUtils.createClientSecret();
        }

        return null;
    }



    /**
     * code 가 유효한지 Apple Server에 검증 요청
     *
     * @param client_secret
     * @param code
     * @return
     */
    @Override
    public TokenResponse requestCodeValidations(String client_secret, String code) {

        TokenResponse tokenResponse = new TokenResponse();

        if (client_secret != null && code != null) {
            tokenResponse = appleUtils.validateAuthorizationGrantCode(client_secret, code);
        }

        return tokenResponse;
    }

    /**
     * refresh_token가 유효한지 Apple Server에 검증 요청
     * 하루에 한번.
     *
     * @param client_secret
     * @param refresh_token
     * @return
     */
    @Override
    public TokenResponse requestRefreshTokenValidations(String client_secret, String refresh_token)
    {
        TokenResponse tokenResponse = new TokenResponse();

        if (client_secret != null && refresh_token != null) {
            tokenResponse = appleUtils.validateAnExistingRefreshToken(client_secret, refresh_token);
        }

        return tokenResponse;
    }

    /**
     * Apple login page 호출을 위한 Meta 정보 가져오기
     *
     * @return
     */
    @Override
    public Map<String, String> getLoginMetaInfo() {
        return appleUtils.getMetaInfo();
    }

    /**
     * id_token에서 payload 데이터 가져오기
     *
     * @return
     */
    @Override
    public Payload getPayload(String id_token) {
        return appleUtils.decodeFromIdToken(id_token);
    }
}