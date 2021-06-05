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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class KaKaoAuthService implements AuthService {
    @Autowired
    private KaKaoClient kakaoClient;

    @Autowired
    private UserProfileRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResponse signup(String accessToken) {
        KaKaoUser kaKaoUser = kakaoClient.profile(accessToken);

        try {
            userRepository.save(
                    UserProfile.UserProfileBuilder()
                            .userID(kaKaoUser.getId())
                            .nickname(kaKaoUser.getProperties().getNickname())
                            .reg_date(LocalDateTime.now())
                            .update_date(LocalDateTime.now())
                            .build());

            return new SignUpResponse(
                    jwtTokenProvider.createToken(kaKaoUser.getId()),
                    kaKaoUser.getId(),
                    kaKaoUser.getKakao_account().getEmail(),
                    kaKaoUser.getProperties().getProfile_image()
                    );
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("Alread use exists " + accessToken);
        }
    }

    public AuthResponse signin(String accessToken) {
        KaKaoUser kaKaoUser = kakaoClient.profile(accessToken);

        UserProfile appUserInfo = userRepository.findOneByUserID(kaKaoUser.getId());

        if (appUserInfo == null) {
            throw new UserNotFoundException("user not found");
        }

        return new AuthResponse(
                jwtTokenProvider.createToken(appUserInfo.getUserID())
        );
    }

    public AuthResponse refresh(Long userNo) {
        return new AuthResponse(
                jwtTokenProvider.createToken(userNo)
        );
    }

}
