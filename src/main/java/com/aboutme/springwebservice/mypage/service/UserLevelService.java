package com.aboutme.springwebservice.mypage.service;

import com.aboutme.springwebservice.mypage.model.UserLevelDTO;
import com.aboutme.springwebservice.mypage.model.WeeklyProgressingVO;
import com.aboutme.springwebservice.mypage.entity.UserLevel;
import com.aboutme.springwebservice.mypage.repository.UserLevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserLevelService {

    private UserLevelRepository userLevelRepository;

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
    public ArrayList<ArrayList<WeeklyProgressingVO>> getWeeklyProgressing(UserLevelDTO ulDTO) {

        String[] days= {"월", "화", "수", "목", "금", "토", "일"};
        String[] en_days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        String[] colors = {"red", "yellow", "green", "pink", "purple"};
        long userId = ulDTO.getUser_id();
        ArrayList<ArrayList<WeeklyProgressingVO>> res = new ArrayList<ArrayList<WeeklyProgressingVO>>();

        LocalDate now = LocalDate.now();
        LocalDate monday = LocalDate.of(now.getYear(), now.getMonth(), 1);
        long weeks = ChronoUnit.DAYS.between(monday.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)), now) / 7 + 1;

        monday = monday.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        monday = monday.minusWeeks(1);

        for(int i = 0; i < weeks; i++){
            monday = monday.plusWeeks(1);
            LocalDate nextMonday = monday.plusWeeks(1);

            List<Object[]> resultList = em.createNativeQuery(
                    "SELECT " +
                            "de.color, " +
                            "date_format(qacl.reg_date, '%Y.%m.%d'), " +
                            "date_format(qacl.reg_date, '%a') " +
                            "FROM QnA_Category_Level qacl " +
                            "JOIN QnA_Category qac ON qacl.category_id = qac.seq " +
                            "JOIN Default_Enquiry de ON de.seq = qac.title_id " +
                            "WHERE qac.author_id = :userId " +
                            "AND qacl.reg_date >= date(:monday) " +
                            "AND qacl.reg_date < date(:nextMonday) " +
                            "ORDER BY qacl.reg_date")
                    .setParameter("userId", userId)
                    .setParameter("monday", monday.toString())
                    .setParameter("nextMonday", nextMonday.toString())
                    .getResultList();

            ArrayList<WeeklyProgressingVO> weeklyProgressingList = new ArrayList<WeeklyProgressingVO>();
            for(int j = 0, k = 0; j < 7; j++){
                WeeklyProgressingVO weeklyProgressing;

                if(k < resultList.size() && resultList.get(k)[2].equals(en_days[j])){
                    int color = (Integer)resultList.get(k)[0];
                    k++;


                    weeklyProgressing = new WeeklyProgressingVO(colors[color], days[j], true);
                } else {
                    weeklyProgressing = new WeeklyProgressingVO(null, days[j], false);
                }
                weeklyProgressingList.add(weeklyProgressing);
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
