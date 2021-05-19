package com.aboutme.springwebservice.mypage.service;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.model.response.ResponseBoardList;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class MyPageService {

    private QnACategoryLevelRepository qnACategoryLevelRepository;

    @PersistenceContext
    EntityManager em;

    public List getMyPostList(Long userId, int color) {

        List<Object[]> resultList = em
                .createNamedStoredProcedureQuery(QnACategoryLevel.getMyPostList)
                .setParameter("authorId", userId)
                .setParameter("color", color)
                .getResultList();

        List postList = new ArrayList<ResponseBoardList>();
        for (Object o: resultList){
            Object[] res = (Object[]) o;

            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

            map.put("answerId", res[0]);
            map.put("color", res[1]);
            map.put("question", res[2]);
            map.put("answer", res[3]);
            map.put("shareYN", res[4]);
            map.put("regDate", res[5]);
            map.put("writtenDate", res[6]);

            postList.add(map);
        }

        return postList;
    }
}
