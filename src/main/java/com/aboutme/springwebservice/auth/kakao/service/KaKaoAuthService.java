package com.aboutme.springwebservice.auth.kakao.service;

import com.aboutme.springwebservice.auth.kakao.client.KaKaoClient;
import com.aboutme.springwebservice.auth.kakao.model.KaKaoUser;
import com.aboutme.springwebservice.auth.common.exception.ResourceAlreadyExistsException;
import com.aboutme.springwebservice.auth.common.exception.UserNotFoundException;
import com.aboutme.springwebservice.auth.common.model.response.AuthResponse;
import com.aboutme.springwebservice.auth.common.model.response.SignUpResponse;
import com.aboutme.springwebservice.auth.common.security.service.JwtTokenProvider;
import com.aboutme.springwebservice.auth.common.service.AuthService;
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import com.aboutme.springwebservice.message.controller.PushNotificationController;
import com.aboutme.springwebservice.message.service.FCMService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class KaKaoAuthService implements AuthService {
    @Autowired
    private KaKaoClient kakaoClient;

    @Autowired
    private UserProfileRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PushNotificationController fcmSender;

    @Transactional
    public SignUpResponse signup(String accessToken,String fcmToken) {
        KaKaoUser kaKaoUser = kakaoClient.profile(accessToken);

        try {
            System.out.println(kaKaoUser.toString());
            this.validateDuplicateUser(kaKaoUser.getId());

            userRepository.save(
                    UserProfile.UserProfileBuilder()
                            .userID(kaKaoUser.getId())
                            .nickname(kaKaoUser.getProperties().getNickname())
                            .fcmToken(fcmToken)
                            .reg_date(LocalDateTime.now())
                            .update_date(LocalDateTime.now())
                            .build());

            return new SignUpResponse(
                    jwtTokenProvider.createToken(kaKaoUser.getId()),
                    kaKaoUser.getId(),
                    kaKaoUser.getProperties().getNickname(),
                    kaKaoUser.getKakao_account().getEmail(),
                    kaKaoUser.getProperties().getProfile_image()
                    );
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("Already use exists " + accessToken);
        }
    }

    public AuthResponse signin(String accessToken,String fcmToken) {
        KaKaoUser kaKaoUser = kakaoClient.profile(accessToken);

        UserProfile appUserInfo = userRepository.findOneByUserID(kaKaoUser.getId());

        if (appUserInfo == null) {
            throw new UserNotFoundException("user not found");
        }

        fcmSender.insertFCMToken(fcmToken, kaKaoUser.getId());

        return new AuthResponse(
                jwtTokenProvider.createToken(appUserInfo.getUserID()),
                appUserInfo.getUserID(),
                appUserInfo.getNickname()
        );
    }

    public AuthResponse refresh(Long userNo) {
        return new AuthResponse(
                jwtTokenProvider.createToken(userNo),
                null,
                null
        );
    }

    @Override
    public void validateDuplicateUser(Long userNo) {
        Optional<UserProfile> userProfile = Optional.ofNullable(userRepository.findOneByUserID(userNo));
        userProfile.ifPresent(findUser -> {
                throw new ResourceAlreadyExistsException("Already user exists");
        });
    }
}
