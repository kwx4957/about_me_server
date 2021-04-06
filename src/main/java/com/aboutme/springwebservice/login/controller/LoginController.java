package com.aboutme.springwebservice.login.controller;

import com.aboutme.springwebservice.login.model.CheckAuthVO;
import com.aboutme.springwebservice.login.model.SingUpVO;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    //TODO : access token은 requstattribute로 받아야 되는지 검토 필요.
    @PostMapping("/Login/signup")
    public void signUp(@RequestBody SingUpVO singUpVO)
    {

    }

    @GetMapping("/Login/checkAuth")
    public Boolean checkAuth(@RequestParam CheckAuthVO checkAuthVO)
    {
        return true;
    }
}
