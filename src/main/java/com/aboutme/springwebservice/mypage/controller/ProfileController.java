package com.aboutme.springwebservice.mypage.controller;

import com.aboutme.springwebservice.mypage.model.UserLevelDTO;
import com.aboutme.springwebservice.mypage.model.response.ResponseWeeklyProgressing;
import com.aboutme.springwebservice.mypage.model.response.ResponseProgressing;
import com.aboutme.springwebservice.mypage.service.UserLevelService;
import com.aboutme.springwebservice.mypage.model.ProfileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ArrayList<ResponseProgressing> getProgressing(@PathVariable("userId") long userId){
        // TODO: 본인의 마이페이지인지 아닌지에 따라서 수정해야함
        UserLevelDTO ulDTO = new UserLevelDTO();
        ulDTO.setUser_id(userId);
        ArrayList<UserLevelDTO> resDTOList = userLevelService.getProgressing(ulDTO);

        ArrayList<ResponseProgressing> res = new ArrayList<ResponseProgressing>();
        for(int i = 0; i < resDTOList.size(); i++){
            ResponseProgressing rp = new ResponseProgressing();
            rp.setLevel(resDTOList.get(i).getLevel());
            rp.setColor(resDTOList.get(i).getColor());
            rp.setExperience(resDTOList.get(i).getExperience());

            res.add(rp);
        }

        return res;
    }

    //주차별 진행도
    @GetMapping("/MyPage/WeeklyProgressing/{userId}")
    public ArrayList<ResponseWeeklyProgressing> getMonthlyProgressing(@PathVariable("userId") long userId){
        UserLevelDTO ulDTO = new UserLevelDTO();
        ulDTO.setUser_id(userId);

        return userLevelService.getWeeklyProgressing(ulDTO);
    }
}
