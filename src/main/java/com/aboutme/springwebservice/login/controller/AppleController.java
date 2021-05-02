package com.aboutme.springwebservice.login.controller;

import com.aboutme.springwebservice.login.model.AppsResponse;
import com.aboutme.springwebservice.login.model.SignInRequest;
import com.aboutme.springwebservice.login.model.TokenResponse;
import com.aboutme.springwebservice.login.service.AppleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class AppleController {

    private Logger logger = LoggerFactory.getLogger(AppleController.class);

    @Autowired
    AppleService appleService;

    /**
     * Apple Login 유저 정보를 받은 후 권한 생성
     *
     * @param signInRequest
     * @return
     */
    @PostMapping(value = "/singIn")
    @ResponseBody
    public TokenResponse SignIn(SignInRequest signInRequest) {

        if (signInRequest == null) {
            return null;
        }

        String code = signInRequest.getCode();
        String client_secret = appleService.getAppleClientSecret(signInRequest.getId_token());

        return appleService.requestCodeValidations(client_secret, code);
    }

    /**
     * refresh_token 유효성 검사
     *
     * @param client_secret
     * @param refresh_token
     * @return
     */
    @PostMapping(value = "/refresh")
    @ResponseBody
    public TokenResponse refreshRedirect(@RequestParam String client_secret, @RequestParam String refresh_token) {
        return appleService.requestRefreshTokenValidations(client_secret, refresh_token);
    }

    /**
     * Apple 유저의 이메일 변경, 서비스 해지, 계정 탈퇴에 대한 Notifications을 받는 Controller (SSL - https (default: 443))
     *
     * @param appsResponse
     */
    @PostMapping(value = "/apps/to/endpoint")
    @ResponseBody
    public void appsToEndpoint(@RequestBody AppsResponse appsResponse) {
        logger.debug("[/path/to/endpoint] RequestBody ‣ " + appsResponse.getPayload());
    }

}
