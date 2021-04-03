package com.aboutme.springwebservice.mypage.controller;

import com.aboutme.springwebservice.mypage.model.ProfileVO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ProfileController {
    @PutMapping("/MyPage/profile")
    void updateProfile(@RequestBody ProfileVO profileVO)
    {
        //TODO : 업데이트 구현
    }
}
