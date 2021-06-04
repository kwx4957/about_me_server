package com.aboutme.springwebservice.auth.naver.controller;

import com.aboutme.springwebservice.auth.naver.model.response.AuthResponse;
import com.aboutme.springwebservice.auth.naver.model.response.SignUpResponse;
import com.aboutme.springwebservice.auth.naver.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    public AuthService authService;

    @PostMapping("/signup")
    public SignUpResponse signup(@RequestHeader String token) {
        return authService.signup(token);
    }

    @GetMapping("/signin")
    public AuthResponse signin(@RequestHeader String token) {
        return authService.signin(token);
    }

    @GetMapping("/refresh")
    public AuthResponse refresh(@AuthenticationPrincipal Long userNo) {
        return authService.refresh(userNo);
    }
}
