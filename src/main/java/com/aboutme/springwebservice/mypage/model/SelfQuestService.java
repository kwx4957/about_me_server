package com.aboutme.springwebservice.mypage.model;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;
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
        if(returns.length()>0) return returns;
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
        if(returns.length()>0) return returns;
        else return "db에 저장 내역이 없습니다.";
    }
    @Transactional(readOnly = true)
    public String getSelfQuestionAnswer(QuestionAnswerDTO qaDto) {
        return null;
    }
    // private List<QuestionAnswerDTO> toDto(List<SelfQuest> posts) {
    //     return posts.stream()
    //             .map(QuestionAnswerDTO::new)
    //             .collect(Collectors.toList());
    // }
}
