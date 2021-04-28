package com.aboutme.springwebservice.board.service;

import com.aboutme.springwebservice.board.model.response.ResponseDailyLists;
import com.aboutme.springwebservice.board.repository.BoardDailyRepository;
import com.aboutme.springwebservice.board.repository.Enquiry;
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
}
