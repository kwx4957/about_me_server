package com.aboutme.springwebservice.board.service;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardInfoService {

    private QnACategoryLevelRepository qnACategoryLevelRepository;

    @PersistenceContext
    EntityManager em;

    public Object getPost(Long answerId) {

        List<Object[]> resultList = em
                .createNamedStoredProcedureQuery(QnACategoryLevel.getPost)
                .setParameter("answerId", answerId)
                .getResultList();

        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("answerId", resultList.get(0)[0]);
        map.put("color", resultList.get(0)[1]);
        map.put("question", resultList.get(0)[2]);
        map.put("userId", resultList.get(0)[3]);
        map.put("nickname", resultList.get(0)[4]);
        map.put("answer", resultList.get(0)[5]);
        map.put("shareYN", resultList.get(0)[6]);
        map.put("level", resultList.get(0)[7]);
        map.put("likes", resultList.get(0)[8]);
        map.put("scraps", resultList.get(0)[9]);
        map.put("comments", resultList.get(0)[10]);
        map.put("regDate", resultList.get(0)[11]);
        map.put("writtenDate", resultList.get(0)[12]);

        System.out.println(map);
        return map;
    }
}
