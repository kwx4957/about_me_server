package com.aboutme.springwebservice.mypage.service;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.model.response.ResponseBoardList;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.mypage.model.response.ResponseMyMain;
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

    public List getMyPostList(long userId, int color) {

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
            map.put("level", res[7]);
            map.put("question", res[2]);
            map.put("answer", res[3]);
            map.put("shareYN", res[4]);
            map.put("regDate", res[5]);
            map.put("writtenDate", res[6]);

            postList.add(map);
        }

        return postList;
    }
    public ResponseMyMain getMyprofile(long userId, int color){
        String msg =""; int code =200;
        Object[] resultList =  (Object[]) em.createNativeQuery(
                "SELECT "+
                        "up.nickname, " +
                        "up.introduce , " +
                        "case" +
                        " when ul.color = 0 then 'red' " +
                        " when ul.color = 1 then 'yellow' " +
                        " when ul.color = 2 then 'green' " +
                        " when ul.color = 3 then 'pink' " +
                        " else 'purple' " +
                        "end as color, " +
                        "case " +
                        " when ul.color = 0 then '열정충만' " +
                        " when ul.color = 1 then '소소한일상' " +
                        " when ul.color = 2 then '기억상자' " +
                        " when ul.color = 3 then '관계의미학' " +
                        " else '상상플러스' " +
                        "end as color_tag " +
                        "FROM User_Level ul " +
                        "JOIN User_Profile up on up.user_id = ul.user_id " +
                        "WHERE ul.user_id = :userId " +
                        "ORDER BY ul.level desc ,ul.experience desc " +
                        "limit 1 ")
                .setParameter("userId", userId)
                .getSingleResult();
       // code =  getMyPostList(userId,color).size()>0?code:400;
        msg = getMyPostList(userId,color).size()>0?"ok":"데이터가 없습니다.";
        ResponseMyMain response= new ResponseMyMain();
        //response.setCode(code);
        response.setMessage(msg);
        response.setUser_id((int)userId);
        response.setNickName(resultList[0].toString());
        response.setIntroduce(resultList[1].toString());
        response.setColor(resultList[2].toString());
        response.setColor_tag(resultList[3].toString());
        response.setPostList(getMyPostList(userId,color));
        return response;
    }

    public ResponseMyMain getOtherProfile(long userId, int color){
        String msg =""; int code =200;
        List<Object[]> resultList = em
                .createNamedStoredProcedureQuery(QnACategoryLevel.getMyPostList)
                .setParameter("authorId", userId)
                .setParameter("color", color)
                .getResultList();

        List postList = new ArrayList<ResponseBoardList>();
        for (Object o: resultList){
            Object[] res = (Object[]) o;
            if(res[4].toString().equals("N")) continue;
            else {
                LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

                map.put("answerId", res[0]);
                map.put("color", res[1]);
                map.put("level", res[7]);
                map.put("question", res[2]);
                map.put("answer", res[3]);
                map.put("shareYN", res[4]);
                map.put("regDate", res[5]);
                map.put("writtenDate", res[6]);

                postList.add(map);
            }
        }

        Object[] result =  (Object[]) em.createNativeQuery(
                "SELECT "+
                        "up.nickname, " +
                        "up.introduce , " +
                        "case" +
                        " when ul.color = 0 then 'red' " +
                        " when ul.color = 1 then 'yellow' " +
                        " when ul.color = 2 then 'green' " +
                        " when ul.color = 3 then 'pink' " +
                        " else 'purple' " +
                        "end as color, " +
                        "case " +
                        " when ul.color = 0 then '열정충만' " +
                        " when ul.color = 1 then '소소한일상' " +
                        " when ul.color = 2 then '기억상자' " +
                        " when ul.color = 3 then '관계의미학' " +
                        " else '상상플러스' " +
                        "end as color_tag " +
                        "FROM User_Level ul " +
                        "JOIN User_Profile up on up.user_id = ul.user_id " +
                        "WHERE ul.user_id = :userId " +
                        "ORDER BY ul.level desc ,ul.experience desc " +
                        "limit 1 ")
                .setParameter("userId", userId)
                .getSingleResult();
        //code =  postList.size()>0?code:400;
        msg = postList.size()>0?"ok":"데이터가 없습니다";
        ResponseMyMain response= new ResponseMyMain();
        //response.setCode(code);
        response.setMessage(msg);
        response.setUser_id((int)userId);
        response.setNickName(result[0].toString());
        response.setIntroduce(result[1].toString());
        response.setColor(result[2].toString());
        response.setColor_tag(result[3].toString());
        response.setPostList(postList);
        return response;
    }
}
