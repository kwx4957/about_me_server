package com.aboutme.springwebservice.auth.naver.service;

import com.aboutme.springwebservice.auth.naver.client.NaverClient;
import com.aboutme.springwebservice.auth.naver.exception.ResourceAlreadyExistsException;
import com.aboutme.springwebservice.auth.naver.exception.UserNotFoundException;
import com.aboutme.springwebservice.auth.naver.model.NaverUser;
import com.aboutme.springwebservice.auth.naver.model.response.AuthResponse;
import com.aboutme.springwebservice.auth.naver.model.response.SignUpResponse;
import com.aboutme.springwebservice.auth.naver.security.service.JwtTokenProvider;
import com.aboutme.springwebservice.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    private NaverClient naverClient;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResponse signup(String naverAccessToken) {
        NaverUser naverUser = naverClient.profile(naverAccessToken);

        try {
            userRepository.save(
                    UserProfile.UserProfileBuilder()
                        .userID(naverUser.getUserId())
                        .nickname(naverUser.getUserName())
                        .reg_date(LocalDateTime.now())
                        .update_date(LocalDateTime.now())
                        .build());

            return new SignUpResponse(
                    jwtTokenProvider.createToken(naverUser.getUserId()),
                    naverUser.getUserId(),
                    naverUser.getEmail(),
                    naverUser.getProfileImage()
            );
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("Alread use exists " + naverAccessToken);
        }
    }

    public AuthResponse signin(String naverAccessToken) {
        NaverUser naverUser = naverClient.profile(naverAccessToken);

        System.out.println("[NaverUser] " + naverUser.toString());
        UserProfile appUserInfo = userRepository.findByUserID(naverUser.getUserId())
                .orElseThrow(() -> new UserNotFoundException("user not found"));

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
