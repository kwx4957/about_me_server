package com.aboutme.springwebservice.auth.naver.service;

import com.aboutme.springwebservice.auth.naver.client.NaverClient;
import com.aboutme.springwebservice.auth.common.exception.ResourceAlreadyExistsException;
import com.aboutme.springwebservice.auth.common.exception.UserNotFoundException;
import com.aboutme.springwebservice.auth.naver.model.NaverUser;
import com.aboutme.springwebservice.auth.common.model.response.AuthResponse;
import com.aboutme.springwebservice.auth.common.model.response.SignUpResponse;
import com.aboutme.springwebservice.auth.common.security.service.JwtTokenProvider;
import com.aboutme.springwebservice.auth.common.service.AuthService;
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import com.aboutme.springwebservice.message.controller.PushNotificationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NaverAuthService implements AuthService {
    @Autowired
    private NaverClient naverClient;

    @Autowired
    private UserProfileRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PushNotificationController fcmSender;

    @Transactional
    public SignUpResponse signup(String naverAccessToken,String fcmToken) {
        NaverUser naverUser = naverClient.profile(naverAccessToken);

        try {
            this.validateDuplicateUser(naverUser.getUserId());

            userRepository.save(
                    UserProfile.UserProfileBuilder()
                        .userID(naverUser.getUserId())
                        .nickname(naverUser.getUserName())
                        .fcmToken(fcmToken)
                        .reg_date(LocalDateTime.now())
                        .update_date(LocalDateTime.now())
                        .build());

            return new SignUpResponse(
                    jwtTokenProvider.createToken(naverUser.getUserId()),
                    naverUser.getUserId(),
                    naverUser.getUserName(),
                    naverUser.getEmail(),
                    naverUser.getProfileImage()
            );
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("Already use exists " + naverAccessToken);
        }
    }

    public AuthResponse signin(String naverAccessToken, String fcmToken) {
        NaverUser naverUser = naverClient.profile(naverAccessToken);

        System.out.println("[NaverUser] " + naverUser.toString());
        UserProfile appUserInfo = userRepository.findOneByUserID(naverUser.getUserId());

        if (appUserInfo == null) {
            throw new UserNotFoundException("user not found");
        }

        fcmSender.insertFCMToken(fcmToken, naverUser.getUserId());

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
