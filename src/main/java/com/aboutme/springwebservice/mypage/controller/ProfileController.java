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
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
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
        //TODO : 업데이트 구현
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
    public ResponseEntity<? extends BasicResponse> getCrushList(@PathVariable("userId") long userId , @PathVariable("crush") String crush){
        return userCrushService.crushLists(userId,crush);
    }
    // 마이버튼 누를시 프로필및 내가쓴글 리스트 출력
    @GetMapping("/MyPage/{userId}")
    public ResponseMyMain enterMyProfile(@PathVariable("userId") int userId){
        Object[] resultList =  (Object[]) em.createNativeQuery(
                "SELECT "+
                        "up.nickname, " +
                        "up.introduce , " +
                        "case" +
                        " when ul.color = 0 then 'red' " +
                        " when ul.color = 1 then 'yellow' " +
                        " when ul.color = 2 then 'green' " +
                        " when ul.color = 3 then 'pink' " +
                        " else 'purple' " +
                        "end as color, " +
                        "case " +
                        " when ul.color = 0 then '열정충만' " +
                        " when ul.color = 1 then '소소한일상' " +
                        " when ul.color = 2 then '기억상자' " +
                        " when ul.color = 3 then '관계의미학' " +
                        " else '상상플러스' " +
                        "end as color_tag " +
                        "FROM User_Level ul " +
                        "JOIN User_Profile up on up.user_id = ul.user_id " +
                        "WHERE ul.user_id = :userId " +
                        "ORDER BY ul.level desc ,ul.experience desc " +
                        "limit 1 ")
                .setParameter("userId", userId)
                .getSingleResult();
        ResponseMyMain response= new ResponseMyMain();
        response.setUser_id(userId);
        response.setNickName(resultList[0].toString());
        response.setIntroduce(resultList[1].toString());
        response.setColor(resultList[2].toString());
        response.setColor_tag(resultList[3].toString());
        // 여기에 내가쓴글 붙이기;
        return response;
    }
}
