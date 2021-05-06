package com.aboutme.springwebservice.mypage.controller;

import com.aboutme.springwebservice.mypage.model.*;
import com.aboutme.springwebservice.mypage.model.response.ResponseWeeklyProgressing;
import com.aboutme.springwebservice.mypage.model.response.ResponseProgressing;
import com.aboutme.springwebservice.mypage.service.UserLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController

public class ProfileController {
    @Autowired
    private UserLevelService userLevelService;

//    @GetMapping("/")
//    void test(@RequestParam("userId") Long userId, @RequestParam("color") int color){
//        userLevelService.updateUserLevelExperience(userId, color, true);
//        return;
//    }

    @PutMapping("/MyPage/profile")
    void updateProfile(@RequestBody ProfileVO profileVO)
    {
        //TODO : 업데이트 구현
    }

    // 진행도
    @GetMapping("/MyPage/Progressing/{userId}")
    public ResponseProgressing getProgressing(@PathVariable("userId") long userId){
        // TODO: 본인의 마이페이지인지 아닌지에 따라서 수정해야함
        UserLevelDTO ulDTO = new UserLevelDTO();
        String[] colors = {"red", "yellow", "green", "pink", "purple"};

        ulDTO.setUser_id(userId);
        ArrayList<UserLevelDTO> resDTOList = userLevelService.getProgressing(ulDTO);

        ArrayList<ProgressingVO> pr = new ArrayList<ProgressingVO>();
        for(int i = 0; i < resDTOList.size(); i++){
            int level = resDTOList.get(i).getLevel();
            int color = resDTOList.get(i).getColor();
            float exp = (float)resDTOList.get(i).getExperience()/100;
            ProgressingVO rp = new ProgressingVO(colors[color], level, exp);

            pr.add(rp);
        }

        return new ResponseProgressing(200, pr);
    }

    //주차별 진행도
    @GetMapping("/MyPage/WeeklyProgressing/{userId}")
    public ResponseWeeklyProgressing getMonthlyProgressing(@PathVariable("userId") long userId, RequestWeeklyProgressing requestWeeklyProgressing){
        UserLevelDTO ulDTO = new UserLevelDTO();
        ulDTO.setUser_id(userId);
        ArrayList<ArrayList<WeeklyProgressingVO>> resList = userLevelService.getWeeklyProgressing(ulDTO, requestWeeklyProgressing);

        return new ResponseWeeklyProgressing(200, resList);
    }
}
