package com.aboutme.springwebservice.board.service;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.model.DailyAnswerDTO;
import com.aboutme.springwebservice.board.model.DailyQuestDTO;
import com.aboutme.springwebservice.board.model.response.ResponseDailyLists;
import com.aboutme.springwebservice.board.entity.DefaultEnquiry;
import com.aboutme.springwebservice.board.repository.DefaultEnquiryRepository;
import com.aboutme.springwebservice.board.entity.QnACategory;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryRepository;
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

    private final DefaultEnquiryRepository DailyRepository;
    private final QnACategoryRepository qnACategoryRepository;
    private final QnACategoryLevelRepository qnACategoryLevelRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public ResponseDailyLists getDailyColors(int userId) {
        StoredProcedureQuery spqq =
                em.createNamedStoredProcedureQuery(DefaultEnquiry.getDailyList);
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
                em.createNamedStoredProcedureQuery(QnACategory.setDaily_step1);
        spqq.setParameter("_user", ques.getUser());
        spqq.setParameter("_title", ques.getTitle());
        spqq.setParameter("_color", ques.getColor());
        spqq.execute();
        return (int) spqq.getOutputParameterValue("RESULT");
    }
    @Transactional(readOnly = true)
    public ResponseDailyLists setDailyStep2(DailyAnswerDTO ans) {
        ResponseDailyLists dailyLists = new ResponseDailyLists();

        StoredProcedureQuery sp =
                em.createNamedStoredProcedureQuery(QnACategoryLevel.setDaily_step2);
        sp.setParameter("_category",ans.getCategory_seq());
        sp.setParameter("_level",ans.getLevel());
        sp.setParameter("_answer", ans.getAnswer());
        sp.setParameter("_share",  Character.toString( ans.getShare()));
        sp.execute();

        Object[] daily = (Object[]) sp.getSingleResult();
        List list_02 = new ArrayList<Object>();

        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("cardSeq", daily[1]);
        map.put("quest_id", daily[2]);
        map.put("question", daily[3]);
        map.put("answer", daily[4]);
        map.put("level", daily[5]);
        map.put("isShare", daily[6]);
        map.put("color", daily[7]);
        map.put("answer_id", daily[8]);//정보추가
        list_02.add(map);

        dailyLists.setUser(Integer.parseInt(daily[0].toString()));
        dailyLists.setDailyLists(list_02);

        return dailyLists;
    }

    @Transactional(readOnly = true)
    public Boolean isDailyWirtten(long userId) {
        Boolean isDailyWirttenBy = false;
        List<QnACategoryLevel> today_list  = qnACategoryLevelRepository.selectTodate();
        for (QnACategoryLevel o : today_list) {
            QnACategory title = qnACategoryRepository.findBySeq(o.getCategoryId());
            if(title.getAuthorId() == userId){
                isDailyWirttenBy = true;
                break;
            }
        }
        return isDailyWirttenBy;
    }
}
