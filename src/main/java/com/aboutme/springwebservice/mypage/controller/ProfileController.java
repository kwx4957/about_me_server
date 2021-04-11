package com.aboutme.springwebservice.mypage.controller;

import com.aboutme.springwebservice.mypage.model.ProfileVO;
import com.aboutme.springwebservice.mypage.model.ProgressingVO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController

public class ProfileController {
    @PutMapping("/MyPage/profile")
    void updateProfile(@RequestBody ProfileVO profileVO)
    {
        //TODO : 업데이트 구현
    }

    @GetMapping("/MyPage/Progressing")
    public ArrayList<ProgressingVO> getProgressing(@RequestParam(value = "user_id", required = true) int user_id){
        return null;
    }
}
