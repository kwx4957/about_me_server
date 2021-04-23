package com.aboutme.springwebservice.mypage.model;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;

import net.minidev.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;
import com.google.gson.JsonArray;
import java.util.stream.Collectors;

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
        spq2.setParameter("_stages", qaDto.getStage());
        spq2.setParameter("_levels", qaDto.getLevels());
        spq2.execute();

        String returns = spq2.getOutputParameterValue("RESULT").toString();
        if(returns.equals("수정 완료")) return returns;
        else return "db에 저장 내역이 없습니다.";
    }
    @Transactional(readOnly = true)
    public String getSelfQuestList(QuestionAnswerDTO dto) {
        StoredProcedureQuery spqq =
                em.createNamedStoredProcedureQuery(SelfQuest.getList10Q10A);
        spqq.setParameter("_user", dto.getUser());
        spqq.setParameter("_stages", dto.getStage());
        spqq.setParameter("_theme", dto.getTheme());
        spqq.execute();
        List lists = spqq.getResultList();

        JsonObject obj = new JsonObject();
        obj.addProperty("user_id",dto.getUser());
        obj.addProperty("stage_num",dto.getStage());
        obj.addProperty("stage_name",dto.getTheme());

        JsonArray resArr = new JsonArray();
        for (Object o : lists) {
            Object[] res = (Object[]) o; // 결과가 둘 이상일 경우 Object[]
            JsonObject data = new JsonObject();
            data.addProperty("level",res[1].toString());
            data.addProperty("question", res[3].toString());
            data.addProperty("answer",  res[4].toString());
            data.addProperty("timer",  res[5].toString());
            resArr.add(data);
        }
        obj.add("result",resArr);
        return obj.toString();
    }
    @Transactional(readOnly = true)
    public String getThemeList(int userid) {
        StoredProcedureQuery spq =
                em.createNamedStoredProcedureQuery(SelfQuest.getTheme10Q10A);
        spq.setParameter("_user", userid);
        spq.execute();
        List lists = spq.getResultList();

        JsonObject obj2 = new JsonObject();
        JsonArray resArr = new JsonArray();
        for (Object o : lists) {
            Object[] res = (Object[]) o; // 결과가 둘 이상일 경우 Object[]
            JsonObject data = new JsonObject();
            data.addProperty("stage_num",Integer.parseInt(res[1].toString()));
            data.addProperty("stage_name", res[0].toString());
            data.addProperty("rate",  res[2].toString());
            data.addProperty("timer",  res[3].toString());
            resArr.add(data);
        }
        obj2.add("result",resArr);
        return obj2.toString();
    }
    // private List<QuestionAnswerDTO> toDto(List<SelfQuest> posts) {
    //     return posts.stream()
    //             .map(QuestionAnswerDTO::new)
    //             .collect(Collectors.toList());
    // }
}
