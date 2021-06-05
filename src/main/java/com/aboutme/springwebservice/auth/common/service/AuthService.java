package com.aboutme.springwebservice.auth.common.service;

import com.aboutme.springwebservice.auth.common.model.response.AuthResponse;
import com.aboutme.springwebservice.auth.common.model.response.SignUpResponse;

public interface AuthService {
    public SignUpResponse signup(String accessToken);
    public AuthResponse signin(String accessToken);
    public AuthResponse refresh(Long userNo) ;
}
