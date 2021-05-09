package com.aboutme.springwebservice.mypage.controller;

import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.mypage.model.ProfileVO;
import com.aboutme.springwebservice.mypage.model.ProgressingVO;
import com.aboutme.springwebservice.mypage.model.UserLevelDTO;
import com.aboutme.springwebservice.mypage.model.WeeklyProgressingVO;
import com.aboutme.springwebservice.mypage.model.response.ResponseCrushList;
import com.aboutme.springwebservice.mypage.model.response.ResponseWeeklyProgressing;
import com.aboutme.springwebservice.mypage.model.response.ResponseProgressing;
import com.aboutme.springwebservice.mypage.service.UserCrushService;
import com.aboutme.springwebservice.mypage.service.UserLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

@RestController
public class ProfileController {
    @Autowired
    private UserLevelService userLevelService;
    @Autowired
    private UserCrushService userCrushService;

//    @Autowired
//    private UserInfoRepository userInfoRepository;

    @PutMapping("/MyPage/profile")
    void updateProfile(@RequestBody ProfileVO profileVO)
    {
        //TODO : 업데이트 구현
    }

    // 진행도
    @GetMapping("/MyPage/Progressing/{userId}")
    public ResponseProgressing getProgressing(@PathVariable("userId") long userId){

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
    public ResponseWeeklyProgressing getMonthlyProgressing(@PathVariable("userId") long userId){

        UserLevelDTO ulDTO = new UserLevelDTO();
        ulDTO.setUser_id(userId);
        ArrayList<ArrayList<WeeklyProgressingVO>> resList = userLevelService.getWeeklyProgressing(ulDTO);

        LocalDate now = LocalDate.now();
        LocalDate monday = LocalDate.of(now.getYear(), now.getMonth(), 1);
        int weeks = (int)ChronoUnit.DAYS.between(monday.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)), now) / 7 + 1;

        String date = null;
        switch(weeks) {
            case 1: {
                date = "2021년 " + now.getMonthValue() + "월 첫째주";
                break;
            }
            case 2: {
                date = "2021년 " + now.getMonthValue() + "월 둘째주";
                break;
            }
            case 3: {
                date = "2021년 " + now.getMonthValue() + "월 셋째주";
                break;
            }
            case 4: {
                date = "2021년 " + now.getMonthValue() + "월 넷째주";
                break;
            }
            case 5: {
                date = "2021년 " + now.getMonthValue() + "월 다섯째주";
            }
        }

        return new ResponseWeeklyProgressing(200, "OK", date, resList);
    }

    //crush는 likes or scarp 으로 접근
    @GetMapping("/MyPage/CrushList/{userId}/{crush}")
    public ResponseEntity<? extends BasicResponse> getCrushList(@PathVariable("userId") long userId , @PathVariable("crush") String crush){
        return userCrushService.crushLists(userId,crush);
    }
}
