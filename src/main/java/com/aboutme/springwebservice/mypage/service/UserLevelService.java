package com.aboutme.springwebservice.mypage.service;

import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import com.aboutme.springwebservice.mypage.model.DayColor;
import com.aboutme.springwebservice.mypage.model.UserLevelDTO;
import com.aboutme.springwebservice.mypage.model.WeeklyProgressingVO;
import com.aboutme.springwebservice.mypage.entity.UserLevel;
import com.aboutme.springwebservice.mypage.repository.UserLevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserLevelService {

    private UserLevelRepository userLevelRepository;
    private UserProfileRepository profileRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public ArrayList<UserLevelDTO> getProgressing(UserLevelDTO ulDTO) {
        long userId = ulDTO.getUser_id();
        ArrayList<UserLevel> ul = new ArrayList<UserLevel>(5);

        ul = userLevelRepository.getProgressingByUserId(userId);

        ArrayList<UserLevelDTO> resDTOList = new ArrayList<UserLevelDTO>(5);
        for(int i = 0; i < ul.size(); i++){
            UserLevelDTO ulReturnDTO = new UserLevelDTO();
            ulReturnDTO.setLevel(ul.get(i).getLevel());
            ulReturnDTO.setColor(ul.get(i).getColor());
            ulReturnDTO.setExperience(ul.get(i).getExperience());

            resDTOList.add(ulReturnDTO);
        }

        return resDTOList;
    }

    @Transactional
    public ArrayList<WeeklyProgressingVO> getWeeklyProgressing(UserLevelDTO ulDTO, int _year, int _month, int _weeks, int max_weeks, LocalDate n) {

        String[] days= {"월", "화", "수", "목", "금", "토", "일"};
        String[] en_days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        String[] colors = {"red", "yellow", "green", "pink", "purple"};
        long userId = ulDTO.getUser_id();
        ArrayList<WeeklyProgressingVO> res = new ArrayList<WeeklyProgressingVO>();

        LocalDate now = n;
        LocalDate monday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        int weeks = _weeks - 1;
        while((weeks--) != 0){
            monday = monday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        }

        monday = monday.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        monday = monday.minusWeeks(1);

        for(int i = 0; i < _weeks; i++){
            monday = monday.plusWeeks(1);
            LocalDate nextMonday = monday.plusWeeks(1);

            List<Object[]> resultList = em
                    .createNamedStoredProcedureQuery(UserLevel.getWeeklyProgressing)
                    .setParameter("userId", userId)
                    .setParameter("monday", monday)
                    .setParameter("nextMonday", nextMonday)
                    .getResultList();

            String date = null;
            switch(i) {
                case 0: {
                    date = _year + "년 " + _month + "월 첫째주";
                    break;
                }
                case 1: {
                    date = _year + "년 " + _month + "월 둘째주";
                    break;
                }
                case 2: {
                    date = _year + "년 " + _month + "월 셋째주";
                    break;
                }
                case 3: {
                    date = _year + "년 " + _month + "월 넷째주";
                    break;
                }
                case 4: {
                    date = _year + "년 " + _month + "월 다섯째주";
                    break;
                }
            }


            WeeklyProgressingVO weeklyProgressingList = new WeeklyProgressingVO();
            weeklyProgressingList.setDate(date);
            if(i < _weeks){
                ArrayList<DayColor> dayColors = new ArrayList<DayColor>();
                for(int j = 0, k = 0; j < 7; j++){
                    if(k < resultList.size() && resultList.get(k)[2].equals(en_days[j])){
                        int color = (Integer)resultList.get(k)[0];
                        k++;

                        dayColors.add(new DayColor(colors[color], days[j], true));
                    } else {
                        dayColors.add(new DayColor(null, days[j], false));
                    }
                    weeklyProgressingList.setWeek(dayColors);
                }
            }
            else {
                weeklyProgressingList.setWeek(null);
            }

            res.add(weeklyProgressingList);
        }

        return res;
    }

    // 카드를 작성했을 때 userlevel의 level, experience수정
    // isDelete 가 true일 때에는 experience를 감소시킨다.
    @Transactional
    public void updateUserLevelExperience(long userId, int color, boolean isDelete) {
        ArrayList<UserLevel> ul = userLevelRepository.getProgressingByUserId(userId);

        UserLevelDTO ulDTO = new UserLevelDTO(ul.get(color));

        int level = ulDTO.getLevel();
        int exp = ulDTO.getExperience();
        if(isDelete){   // 작성한 카드를 삭제했을 때
            if((--exp) == -1) { // exp가 0이면
                if(level != 1) {
                    --level;
                    exp = 99;
                }
                else {
                    exp = 0;
                }
            }
        }
        else {  // 카드를 작성했을 때
            // exp가 100이면 레벨 1 증가
            level += (++exp) / 100;
            exp %= 100;
        }

        ulDTO.setLevel(level);
        ulDTO.setExperience(exp);

        userLevelRepository.updateUserLevelExperience(ulDTO.toEntity());

        return;
    }

}
