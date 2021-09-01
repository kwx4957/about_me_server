package com.aboutme.springwebservice.auth.apple.controller;

import com.aboutme.springwebservice.auth.apple.domainModel.User;
import com.aboutme.springwebservice.auth.apple.model.*;
import com.aboutme.springwebservice.auth.apple.repository.AppleUserRepository;
import com.aboutme.springwebservice.auth.apple.service.AppleService;

import com.aboutme.springwebservice.auth.common.exception.ResourceAlreadyExistsException;
import com.aboutme.springwebservice.auth.common.exception.UserNotFoundException;
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/apple/auth")
public class AppleController {

    private Logger logger = LoggerFactory.getLogger(AppleController.class);

    @Autowired
    AppleService appleService;

    @Autowired
    UserProfileRepository appleUserRepository;

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

        Payload payload = appleService.getPayload(signUpRequest.getId_token());

        TokenResponse response = new TokenResponse();

        long userId = payload.getEmail().hashCode();
        this.validateDuplicateUser(userId);

        UserProfile user = UserProfile.builder(userId)
                .reg_date(LocalDateTime.now())
                .update_date(LocalDateTime.now())
                .email(payload.getEmail())
                .build();

        appleUserRepository.save(user);

        response.setUserId(userId);
        return response;
    }

    @PostMapping(value = "/signIn")
    @ResponseBody
    public TokenResponse SignIn(SignUpRequest signUpRequest) {

        if (signUpRequest == null) {
            return null;
        }

        Payload payload = appleService.getPayload(signUpRequest.getId_token());

        TokenResponse response = new TokenResponse();

        long userId = payload.getEmail().hashCode();
        UserProfile appUserInfo = appleUserRepository.findOneByUserID(userId);

        if (appUserInfo == null) {
            throw new UserNotFoundException("user not found");
        }

        response.setUserId(userId);
        return response;
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

    public void validateDuplicateUser(Long userNo) {
        Optional<UserProfile> userProfile = Optional.ofNullable(appleUserRepository.findOneByUserID(userNo));
        userProfile.ifPresent(findUser -> {
            throw new ResourceAlreadyExistsException("Already user exists");
        });
    }
    
}
