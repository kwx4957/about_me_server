package com.aboutme.springwebservice.mypage.service;

import com.aboutme.springwebservice.mypage.model.QuestionAnswerDTO;
import com.aboutme.springwebservice.mypage.model.response.ResponseSelfQnAList;
import com.aboutme.springwebservice.mypage.model.response.ResponseThemeList;
import com.aboutme.springwebservice.mypage.entity.SelfQuest;
import com.aboutme.springwebservice.mypage.repository.SelfQuestRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.*;


@RequiredArgsConstructor
@Service
public class SelfQuestService {

    private final SelfQuestRepository selfQuestRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public String createSelfQuestionAnswer(QuestionAnswerDTO qaDto) {

        StoredProcedureQuery spq =
                em.createNamedStoredProcedureQuery(SelfQuest.set10Q10A);
        spq.setParameter("_user", qaDto.getUser());
        spq.setParameter("_title", qaDto.getTitle());
        spq.setParameter("_answer", qaDto.getAnswer());
        spq.setParameter("_theme", qaDto.getTheme());
        spq.setParameter("_stages", qaDto.getStage());
        spq.setParameter("_levels", qaDto.getLevels());
        spq.execute();

        String returns = spq.getOutputParameterValue("RESULT").toString();
        if(returns.equals("저장 완료")) return returns;
        else return "db에 저장 내역이 없습니다.";
    }
    @Transactional(readOnly = true)
    public String updateSelfQuestionAnswer(QuestionAnswerDTO qaDto) {

        StoredProcedureQuery spq2 =
                em.createNamedStoredProcedureQuery(SelfQuest.update10Q10A);
        spq2.setParameter("_user", qaDto.getUser());
        spq2.setParameter("_title", qaDto.getTitle());
        spq2.setParameter("_answer", qaDto.getAnswer());
        spq2.setParameter("_theme", qaDto.getTheme());
        spq2.setParameter("_new", qaDto.getTheme_new());
        spq2.setParameter("_stages", qaDto.getStage());
        spq2.setParameter("_levels", qaDto.getLevels());
        spq2.execute();

        String returns = spq2.getOutputParameterValue("RESULT").toString();
        if(returns.equals("수정 완료")) return returns;
        else if(returns.equals("수정하려는 제목은 이미 존재하는 제목입니다.")) return returns;
        else return "db에 저장 내역이 없습니다.";
    }
    @Transactional(readOnly = true)
    public ResponseSelfQnAList getSelfQuestList(int userId, int stage, String theme) {
        StoredProcedureQuery spqq =
                em.createNamedStoredProcedureQuery(SelfQuest.getList10Q10A);
        spqq.setParameter("_user", userId);
        spqq.setParameter("_stages", stage);
        spqq.setParameter("_theme", theme);
        spqq.execute();
        List lists = spqq.getResultList();

        List list_01 = new ArrayList<Object>(); // 매핑 한거 받는 타입입
       for (Object o : lists) {
            Object[] res = (Object[]) o;
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("seq",res[0]);
            map.put("levels",res[1].toString());
            map.put("question",res[2].toString());
            map.put("answer",res[3].toString());
            map.put("postOn",res[4].toString());
            list_01.add(map);
        }
        ResponseSelfQnAList responseSelfQnAList = new ResponseSelfQnAList();
        responseSelfQnAList.setUser(userId);
        responseSelfQnAList.setStage(stage);
        responseSelfQnAList.setTheme(theme);
        responseSelfQnAList.setAnswerLists(list_01);

        return responseSelfQnAList;
    }
    @Transactional(readOnly = true)
    public String delSelfQuestTheme(int userId, int stages, String theme) {

        return null;
    }
    @Transactional(readOnly = true)
    public ResponseThemeList getThemeList(int userId) {

        StoredProcedureQuery spq =
                em.createNamedStoredProcedureQuery(SelfQuest.getTheme10Q10A);
        spq.setParameter("_user", userId);
        spq.execute();

        List lists = spq.getResultList();
        List list_02 = new ArrayList<Object>();

        for (Object o : lists){
            Object[] res = (Object[]) o;
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("stage_num",res[1].toString());
            map.put("stage_name",res[0]);
            map.put("rate",res[2].toString());
            map.put("timer",res[3].toString());
            list_02.add(map);
        }
        ResponseThemeList responseList = new ResponseThemeList();
        responseList.setUser(userId);
        responseList.setThemeLists(list_02);

        return responseList;

    }
    
    // private List<QuestionAnswerDTO> toDto(List<SelfQuest> posts) {
    //     return posts.stream()
    //             .map(QuestionAnswerDTO::new)
    //             .collect(Collectors.toList());
    // }
}
