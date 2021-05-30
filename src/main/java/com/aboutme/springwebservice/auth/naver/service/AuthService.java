package com.aboutme.springwebservice.auth.naver.service;

import com.aboutme.springwebservice.auth.naver.client.NaverClient;
import com.aboutme.springwebservice.auth.naver.exception.ResourceAlreadyExistsException;
import com.aboutme.springwebservice.auth.naver.exception.UserNotFoundException;
import com.aboutme.springwebservice.auth.naver.model.NaverUser;
import com.aboutme.springwebservice.auth.naver.model.response.AuthResponse;
import com.aboutme.springwebservice.auth.naver.security.service.JwtTokenProvider;
import com.aboutme.springwebservice.user.entitiy.AppUserInfo;
import com.aboutme.springwebservice.user.model.request.SignupRequest;
import com.aboutme.springwebservice.user.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService {
    @Autowired
    private NaverClient naverClient;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse signup(String naverAccessToken, SignupRequest signupRequest) {
        NaverUser naverUser = naverClient.profile(naverAccessToken);
        try {
            AppUserInfo appUserInfo = userRepository.save(new AppUserInfo(naverUser.getUserId(), signupRequest.getName(), signupRequest.getEmail(), signupRequest.getProfileImage()));

            return new AuthResponse(
                    jwtTokenProvider.createToken(appUserInfo.getNaverUserId())
            );
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("Alread use exists " + naverAccessToken);
        }
    }

    public AuthResponse signin(String naverAccessToken) {
        NaverUser naverUser = naverClient.profile(naverAccessToken);

        AppUserInfo appUserInfo = userRepository.findByNaverUserId(naverUser.getUserId())
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        return new AuthResponse(
                jwtTokenProvider.createToken(appUserInfo.getNaverUserId())
        );
    }

    public AuthResponse refresh(Long userNo) {
        return new AuthResponse(
                jwtTokenProvider.createToken(userNo)
        );
    }
}
