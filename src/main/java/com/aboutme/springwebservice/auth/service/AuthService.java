package com.aboutme.springwebservice.auth.service;

import com.aboutme.springwebservice.auth.exception.ResourceAlreadyExistsException;
import com.aboutme.springwebservice.auth.exception.UserNotFoundException;
import com.aboutme.springwebservice.auth.kakao.model.KaKaoUser;
import com.aboutme.springwebservice.auth.naver.model.response.AuthResponse;
import com.aboutme.springwebservice.auth.naver.model.response.SignUpResponse;
import com.aboutme.springwebservice.domain.UserProfile;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;

public interface AuthService {
    public SignUpResponse signup(String accessToken);
    public AuthResponse signin(String accessToken);
    public AuthResponse refresh(Long userNo) ;
}
