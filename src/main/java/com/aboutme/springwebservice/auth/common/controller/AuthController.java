package com.aboutme.springwebservice.auth.common.controller;

import com.aboutme.springwebservice.auth.common.AuthType;
import com.aboutme.springwebservice.auth.kakao.service.KaKaoAuthService;
import com.aboutme.springwebservice.auth.common.model.response.AuthResponse;
import com.aboutme.springwebservice.auth.common.model.response.SignUpResponse;
import com.aboutme.springwebservice.auth.naver.service.NaverAuthService;
import com.aboutme.springwebservice.auth.common.service.AuthService;
import com.aboutme.springwebservice.message.controller.PushNotificationController;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    private NaverAuthService naverAuthService;

    @Autowired
    private KaKaoAuthService kaKaoAuthService;

    @PostMapping("/signup")
    public SignUpResponse signup(@RequestHeader String token, @RequestParam AuthType type,@RequestParam String fcmToken) {
        chooseAuthService(type);
        return authService.signup(token,fcmToken);
    }

    @GetMapping("/signin")
    public AuthResponse signin(@RequestHeader String token, @RequestParam AuthType type,@RequestParam String fcmToken ) {
        chooseAuthService(type);
        return authService.signin(token,fcmToken);
    }

    @GetMapping("/refresh")
    public AuthResponse refresh(@AuthenticationPrincipal Long userNo, @RequestParam AuthType type) {
        chooseAuthService(type);
        return authService.refresh(userNo);
    }

    private void chooseAuthService(AuthType type) {
        if (type == AuthType.Naver) {
            authService = naverAuthService;
        } else if(type == AuthType.Kakao) {
            authService = kaKaoAuthService;
        }
    }
}
