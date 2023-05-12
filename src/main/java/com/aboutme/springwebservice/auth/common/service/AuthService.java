package com.aboutme.springwebservice.auth.common.service;

import com.aboutme.springwebservice.auth.common.model.response.AuthResponse;
import com.aboutme.springwebservice.auth.common.model.response.SignUpResponse;

public interface AuthService {
    public SignUpResponse signup(String accessToken, String fcmToken);
    public AuthResponse signin(String accessToken, String fcmToken);
    public AuthResponse refresh(Long userNo);
    public void validateDuplicateUser(Long userNo);
}
