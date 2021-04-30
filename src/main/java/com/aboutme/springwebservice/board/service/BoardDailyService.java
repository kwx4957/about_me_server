package com.aboutme.springwebservice.board.service;

import com.aboutme.springwebservice.board.model.DailyAnswerDTO;
import com.aboutme.springwebservice.board.model.DailyQuestDTO;
import com.aboutme.springwebservice.board.model.response.ResponseDailyLists;
import com.aboutme.springwebservice.board.repository.BoardDailyRepository;
import com.aboutme.springwebservice.board.repository.CategoryAnswer;
import com.aboutme.springwebservice.board.repository.CategoryQuestion;
import com.aboutme.springwebservice.board.repository.Enquiry;
import com.aboutme.springwebservice.mypage.model.response.ResponseThemeList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardDailyService{

    private final BoardDailyRepository boardDailyRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public ResponseDailyLists getDailyColors(int userId) {
        StoredProcedureQuery spqq =
                em.createNamedStoredProcedureQuery(Enquiry.getDailyList);
        spqq.setParameter("_user", userId);
        spqq.execute();
        List lists = spqq.getResultList();

        List list_01 = new ArrayList<Object>(); // 매핑 한거 받는 타입입
        for (Object o : lists) {
            Object[] res = (Object[]) o;
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("seq",res[0]);
            map.put("lev.",res[3].toString());
            map.put("color",res[1].toString());
            map.put("question",res[2].toString());
            list_01.add(map);
        }
        ResponseDailyLists dailyLists = new ResponseDailyLists();
        dailyLists.setUser(userId);
        dailyLists.setDailyLists(list_01);
        return dailyLists;
    }
    @Transactional(readOnly = true)
    public int setDailyStep1(DailyQuestDTO ques) {
        StoredProcedureQuery spqq =
                em.createNamedStoredProcedureQuery(CategoryQuestion.setDaily_step1);
        spqq.setParameter("_user", ques.getUser());
        spqq.setParameter("_title", ques.getTitle());
        spqq.setParameter("_color", ques.getColor());
        spqq.execute();
        return (int) spqq.getOutputParameterValue("RESULT");
    }
    @Transactional(readOnly = true)
    public ResponseDailyLists setDailyStep2(DailyAnswerDTO ans) {
        StoredProcedureQuery spqq =
                em.createNamedStoredProcedureQuery(CategoryAnswer.setDaily_step2);
        spqq.setParameter("_category",ans.getCategory_seq());
        spqq.setParameter("_level",ans.getLevel());
        spqq.setParameter("_answer", ans.getAnswer());
        spqq.setParameter("_share", ans.getShare());
        spqq.execute();

        List lists = spqq.getResultList();
        List list_02 = new ArrayList<Object>();

        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("cardSeq", lists.get(1));
        map.put("quest_id",lists.get(2));
        map.put("question",lists.get(3));
        map.put("answer",lists.get(4));
        map.put("level",lists.get(5));
        map.put("isShare",lists.get(6));
        map.put("color",lists.get(7));
        list_02.add(map);
        ResponseDailyLists dailyLists = new ResponseDailyLists();
        dailyLists.setUser((int) lists.get(0));
        dailyLists.setDailyLists(list_02);

        return dailyLists;
    }
}
