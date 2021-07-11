package com.aboutme.springwebservice.auth.apple.controller;

import com.aboutme.springwebservice.auth.apple.domainModel.User;
import com.aboutme.springwebservice.auth.apple.model.*;
import com.aboutme.springwebservice.auth.apple.repository.AppleUserRepository;
import com.aboutme.springwebservice.auth.apple.service.AppleService;
import com.nimbusds.jose.Payload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/apple/auth")
public class AppleController {

    private Logger logger = LoggerFactory.getLogger(AppleController.class);

    @Autowired
    AppleService appleService;

    @Autowired
    AppleUserRepository appleUserRepository;

    /**
     * Apple Login 유저 정보를 받은 후 권한 생성
     *
     * @param signUpRequest
     * @return
     */
    @PostMapping(value = "/signUp")
    @ResponseBody
    public TokenResponse SignUp(SignUpRequest signUpRequest) {

        if (signUpRequest == null) {
            return null;
        }

        String code = signUpRequest.getCode();
        String client_secret = appleService.getAppleClientSecret(signUpRequest.getId_token());
        Payload payload = appleService.getPayload(signUpRequest.getId_token());

        TokenResponse response = appleService.requestCodeValidations(client_secret, code);

        String userId = payload.toJSONObject().get("email").toString();

        User user = User.builder()
                .userId(userId)
                .accesToken(response.getAccess_token())
                .refreshToken(response.getRefresh_token())
                .updateDate(new Date())
                .build();

        appleUserRepository.save(user);

        return response;
    }

    /**
     * Apple Login 유저 정보를 받은 후 권한 생성
     *
     * @param loginRequest
     * @return
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public LoginResponse Login(@RequestParam LoginRequest loginRequest) {

        if (loginRequest == null) {
            return new LoginResponse("실패");
        }

        if(appleUserRepository.findByUserId(loginRequest.getUserID()).getAccesToken() == loginRequest.getAccessToken()) {
            return new LoginResponse("성공");
        }

        //하루단위로 엑세스 토근 업데이트 하는 로직을 만들것인지 확인 필요.
        return new LoginResponse("실패");
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
