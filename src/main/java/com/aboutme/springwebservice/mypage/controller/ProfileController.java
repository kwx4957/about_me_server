package com.aboutme.springwebservice.mypage.controller;

import com.aboutme.springwebservice.board.model.response.ResponseBoardList;
import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserInfoRepository;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.mypage.model.ProfileVO;
import com.aboutme.springwebservice.mypage.model.ProgressingVO;
import com.aboutme.springwebservice.mypage.model.UserLevelDTO;
import com.aboutme.springwebservice.mypage.model.WeeklyProgressingVO;
import com.aboutme.springwebservice.mypage.model.response.ResponseCrushList;
import com.aboutme.springwebservice.mypage.model.response.ResponseMyMain;
import com.aboutme.springwebservice.mypage.model.response.ResponseWeeklyProgressing;
import com.aboutme.springwebservice.mypage.model.response.ResponseProgressing;
import com.aboutme.springwebservice.mypage.service.MyPageService;
import com.aboutme.springwebservice.mypage.service.UserCrushService;
import com.aboutme.springwebservice.mypage.service.UserLevelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ProfileController {
    private UserLevelService userLevelService;
    private UserCrushService userCrushService;
    private MyPageService myPageService;

    public UserInfoRepository userInfoRepository;
    public UserProfileRepository profileRepository;

    @PersistenceContext
    private EntityManager em;

    @PutMapping("/MyPage/profile")
    void updateProfile(@RequestBody ProfileVO profileVO)
    {
        UserProfile userProfile = profileRepository.findOneByUserID(profileVO.getUserId());

        userProfile.setColor(profileVO.getColor());
        userProfile.setIntro(profileVO.getIntroduce());
        userProfile.setNickname(profileVO.getNickName());
        userProfile.setPush_time(profileVO.getPush_time());
        userProfile.setPush_yn(profileVO.getPush_yn());
        userProfile.setUpdate_date(LocalDateTime.now());
        userProfile.setThemeComment(profileVO.getTheme_comment());

        profileRepository.save(userProfile);
    }

    @GetMapping("/MyPage/profile")
    UserProfile getProfile(@PathVariable("userId") long userId)
    {
        return profileRepository.findOneByUserID(userId);
    }

    // 진행도
    @GetMapping("/MyPage/Progressing/{userId}")
    public ResponseProgressing getProgressing(@PathVariable("userId") long userId){

        if(!userInfoRepository.existsById(userId)){
            return new ResponseProgressing(400, "해당 유저가 존재하지 않습니다.", null);
        }

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

        return new ResponseProgressing(200, "OK", pr);
    }

    //주차별 진행도
    @GetMapping("/MyPage/WeeklyProgressing/{userId}")
    public ResponseWeeklyProgressing getMonthlyProgressing(@PathVariable("userId") long userId){

        if (!userInfoRepository.existsById(userId)) {
            return new ResponseWeeklyProgressing(400, "해당 유저가 존재하지 않습니다", null, null);
        }

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

    @GetMapping("/MyPage/PostList/{userId}")
    public ResponseBoardList getMyPostList(@PathVariable("userId") long userId, @RequestParam(value="color", required = false) String color) {
        ResponseBoardList res = new ResponseBoardList();
        int _color; // color 입력 잘 못 되었는지 확인

        if (!userInfoRepository.existsById(userId)) {
            res.setCode(400);
            res.setMessage("해당 유저가 존재하지 않습니다.");

            return res;
        }

        if (color == null) {
            _color = -1;
        }
        else {
            switch (color) {
                case "red":
                    _color = 0;
                    break;
                case "yellow":
                    _color = 1;
                    break;
                case "green":
                    _color = 2;
                    break;
                case "pink":
                    _color = 3;
                    break;
                case "purple":
                    _color = 4;
                    break;
                default:
                    res.setCode(400);
                    res.setMessage("color 입력이 잘 못 되었습니다.");

                    return res;
            }
        }

        res.setCode(200);
        res.setMessage("OK");
        res.setPostList(myPageService.getMyPostList(userId, _color));

        return res;
    }

    //crush는 likes or scarp 으로 접근
    @GetMapping("/MyPage/CrushList/{userId}/{crush}")
    public ResponseMyMain getCrushList(@PathVariable("userId") long userId ,
                                                                @PathVariable("crush") String crush,
                                                                @RequestParam(value="color", required = false) String color)
    {
        ResponseMyMain page = enterMyProfile(userId,color);
        int _color = 0;

        if (color == null) {
            _color = -1;
        }
        else {
            switch (color) {
                case "red":
                    _color = 0;
                    break;
                case "yellow":
                    _color = 1;
                    break;
                case "green":
                    _color = 2;
                    break;
                case "pink":
                    _color = 3;
                    break;
                case "purple":
                    _color = 4;
                    break;
                default:
                    _color = -1;
            }
        }
        page.setMessage("ok");
        page.setPostList(Collections.singletonList(userCrushService.crushLists(userId, crush, _color).getBody()));
        return page;
    }
    // 마이버튼 누를시 프로필및 내가쓴글 리스트 출력
    @GetMapping("/MyPage/{userId}")
    public ResponseMyMain enterMyProfile(@PathVariable("userId") long userId, @RequestParam(value="color", required = false) String color){
        ResponseMyMain res = new ResponseMyMain();
        int _color = -1; // color 입력 잘 못 되었는지 확인
        if (!userInfoRepository.existsById(userId)) {
            res.setCode(400);
            res.setMessage("해당 유저가 존재하지 않습니다.");

            return res;
        }

        if (color == null) {
            _color = -1;
        }
        else {
            switch (color) {
                case "red":
                    _color = 0;
                    break;
                case "yellow":
                    _color = 1;
                    break;
                case "green":
                    _color = 2;
                    break;
                case "pink":
                    _color = 3;
                    break;
                case "purple":
                    _color = 4;
                    break;
                default:
                    res.setCode(400);
                    res.setMessage("color 입력이 잘 못 되었습니다.");

                    return res;
            }
        }

        return myPageService.getMyprofile(userId,_color);
    }
    @GetMapping("/MyPage/{userId}/{otherID}")
    public ResponseMyMain enterOtherProfile(@PathVariable("userId") long userId,
                                         @PathVariable("otherID") long otherID,
                                         @RequestParam(value="color", required = false) String color){
        ResponseMyMain res = new ResponseMyMain();
        int _color = -1; // color 입력 잘 못 되었는지 확인
        if (!userInfoRepository.existsById(otherID)) {
            res.setCode(400);
            res.setMessage("찾으신 유저가 존재하지 않습니다.");

            return res;
        }

        if (color == null) {
            _color = -1;
        }
        else {
            switch (color) {
                case "red":
                    _color = 0;
                    break;
                case "yellow":
                    _color = 1;
                    break;
                case "green":
                    _color = 2;
                    break;
                case "pink":
                    _color = 3;
                    break;
                case "purple":
                    _color = 4;
                    break;
                default:
                    res.setCode(400);
                    res.setMessage("color 입력이 잘 못 되었습니다.");

                    return res;
            }
        }

        return myPageService.getOtherProfile(otherID,_color);
    }
}
