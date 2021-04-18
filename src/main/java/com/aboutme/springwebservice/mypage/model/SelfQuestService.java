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
    public List<QuestionAnswerDTO> store(QuestionAnswerDTO qaDto) {

        StoredProcedureQuery spq =
                em.createNamedStoredProcedureQuery(SelfQuest.set10Q10A);
        spq.setParameter("_user", qaDto.getUser());
        spq.setParameter("_title", qaDto.getTitle());
        spq.setParameter("_answer", qaDto.getAnswer());
        spq.setParameter("_theme", qaDto.getTheme());
        spq.setParameter("_stages", 0);
        spq.setParameter("_levels", 0);
        spq.execute();

        @SuppressWarnings("unchecked")
        List<QuestionAnswerDTO> results = spq.getResultList();
        return results;
    }
    @Transactional(readOnly = true)
    public List<QuestionAnswerDTO> edit(QuestionAnswerDTO qaDto) {

        StoredProcedureQuery spq =
                em.createNamedStoredProcedureQuery(SelfQuest.set10Q10A);
        spq.setParameter("_user", qaDto.getUser());
        spq.setParameter("_title", qaDto.getTitle());
        spq.setParameter("_answer", qaDto.getAnswer());
        spq.setParameter("_theme", qaDto.getTheme());
        spq.setParameter("_stages", qaDto.getStage());
        spq.setParameter("_levels", qaDto.getLevels());
        spq.execute();

        @SuppressWarnings("unchecked")
        List<QuestionAnswerDTO> results = spq.getResultList();
        return results;
    }
    // private List<QuestionAnswerDTO> toDto(List<SelfQuest> posts) {
    //     return posts.stream()
    //             .map(QuestionAnswerDTO::new)
    //             .collect(Collectors.toList());
    // }
}
