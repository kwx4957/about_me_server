package com.aboutme.springwebservice.mypage.service;

import com.aboutme.springwebservice.mypage.model.UserLevelDTO;
import com.aboutme.springwebservice.mypage.model.response.ResponseWeeklyProgressing;
import com.aboutme.springwebservice.mypage.entity.UserLevel;
import com.aboutme.springwebservice.mypage.repository.UserLevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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

        //궁금!
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
    public ArrayList<ResponseWeeklyProgressing> getWeeklyProgressing(UserLevelDTO ulDTO) {
        long userId = ulDTO.getUser_id();

        List<Object[]> resultList = em.createNativeQuery(
                "SELECT " +
                "de.color, " +
                "date_format(qacl.reg_date, '%Y.%m.%d'), " +
                "date_format(qacl.reg_date, '%a') " +
                "FROM QnA_Category_Level qacl " +
                "JOIN QnA_Category qac ON qacl.category_id = qac.seq " +
                "JOIN Default_Enquiry de ON de.seq = qac.title_id " +
                "WHERE qac.author_id = :userId " +
                "ORDER BY qacl.reg_date")
                .setParameter("userId", userId)
                .getResultList();

        ArrayList<ResponseWeeklyProgressing> responseWeeklyProgressings = new ArrayList<ResponseWeeklyProgressing>();
        for(int i = 0; i < resultList.size(); i++){
            ResponseWeeklyProgressing responseWeeklyProgressing = new ResponseWeeklyProgressing();
            responseWeeklyProgressing.setColor((Integer)resultList.get(i)[0]);
            responseWeeklyProgressing.setReg_date((String)resultList.get(i)[1]);
            responseWeeklyProgressing.setDay((String)resultList.get(i)[2]);

            responseWeeklyProgressings.add(responseWeeklyProgressing);
        }

        return responseWeeklyProgressings;
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
