package com.aboutme.springwebservice.board.service;

import com.aboutme.springwebservice.board.entity.BoardInteraction;
import com.aboutme.springwebservice.board.entity.QnACategory;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.model.response.ResponseBoardList;
import com.aboutme.springwebservice.board.repository.BoardInteractionRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.board.repository.QnACommentRepository;
import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.domain.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
@AllArgsConstructor
public class BoardSearchService {

    private QnACategoryLevelRepository qnACategoryLevelRepository;
    private BoardInteractionRepository boardInteractionRepository;

    @PersistenceContext
    EntityManager em;

    @Transactional(readOnly = true)
    public List getLatestPost(int id, int color) {
        UserProfile user= UserProfile.UserProfileBuilder().userID(id).build();
        List<Object[]> resultList = em
                .createNamedStoredProcedureQuery(QnACategoryLevel.getLatestPost)
                .setParameter("color", color)
                .getResultList();

        List postList = new ArrayList<ResponseBoardList>();
        for (Object o: resultList){
            Object[] res = (Object[]) o;

            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            if(Integer.parseInt(res[3].toString())!=id) {
                BoardInteraction boardInteraction = null;
                if(Integer.parseInt(res[7].toString()) > 0 || Integer.parseInt(res[8].toString()) > 0){
                    QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findBySeq(Long.parseLong(res[0].toString()));

                    boardInteraction = boardInteractionRepository.findByBoardAndLikeUser(qnACategoryLevel,user)
                            .orElseGet(() -> BoardInteraction.builder()
                                    .board(qnACategoryLevel)
                                    .likeUser(user)
                                    .likeYn(0)
                                    .scrapYn(0)
                                    .build());
                }

                map.put("answerId", res[0]);
                map.put("color", res[1]);
                map.put("question", res[2]);
                map.put("userId", res[3]);
                map.put("nickname", res[4]);
                map.put("answer", res[5]);
                if(res[6].equals("N")) map.put("shareYN",false);
                else map.put("shareYN",true);
                map.put("level", res[7]);
                map.put("likes", res[8]);
                if(boardInteraction != null && boardInteraction.getLikeYn() == 1){
                    map.put("hasLiked", true);
                }
                else{
                    map.put("hasLiked", false);
                }
                map.put("scraps", res[9]);
                if(boardInteraction != null && boardInteraction.getScrapYn() == 1){
                    map.put("hasScrapped", true);
                }
                else{
                    map.put("hasScrapped", false);
                }
                map.put("comments", res[10]);
                map.put("regDate", res[11]);
                map.put("writtenDate", res[12]);

                postList.add(map);
            }

        }
        return postList;
    }

    @Transactional(readOnly = true)
    public List getHotPosts(int id, String color) {
        UserProfile user= UserProfile.UserProfileBuilder().userID(id).build();
        List<Object[]> resultList = em
                .createNamedStoredProcedureQuery(QnACategoryLevel.getPopularPost)
                .getResultList();

        List postList = new ArrayList<ResponseBoardList>();
        for (Object o: resultList){
            Object[] res = (Object[]) o;
            if((color.equals(res[1].toString())||color.equals("no"))&&((int)res[7]>0)&&(Integer.parseInt(res[3].toString())!=id)) {
                BoardInteraction boardInteraction = null;
                if(Integer.parseInt(res[7].toString()) > 0 || Integer.parseInt(res[8].toString()) > 0){
                    QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findBySeq(Long.parseLong(res[0].toString()));

                    boardInteraction = boardInteractionRepository.findByBoardAndLikeUser(qnACategoryLevel,user)
                            .orElseGet(() -> BoardInteraction.builder()
                                    .board(qnACategoryLevel)
                                    .likeUser(user)
                                    .likeYn(0)
                                    .scrapYn(0)
                                    .build());
                }
                // 색깔태그검색 작동및 좋아요 0개 제외 == 새로운 글이므로
                LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

                map.put("answerId", res[0]);
                map.put("color", res[1]);
                map.put("question", res[2]);
                map.put("userId", res[3]);
                map.put("nickname", res[4]);
                map.put("answer", res[5]);
                if(res[6].equals("N")) map.put("shareYN",false);
                else map.put("shareYN",true);
                map.put("level", res[7]);
                map.put("likes", res[8]);
                if(boardInteraction != null && boardInteraction.getLikeYn() == 1){
                    map.put("hasLiked", true);
                }
                else{
                    map.put("hasLiked", false);
                }
                map.put("scraps", res[9]);
                if(boardInteraction != null && boardInteraction.getScrapYn() == 1){
                    map.put("hasScrapped", true);
                }
                else{
                    map.put("hasScrapped", false);
                }
                map.put("comments", res[10]);
                map.put("regDate", res[11]);
                map.put("writtenDate", res[12]);

                postList.add(map);
            }
        }
        return postList;
    }

    @Transactional(readOnly = true)
    public List getMyPopularList(Long id, int _color) {
        UserProfile user= UserProfile.UserProfileBuilder().userID(id).build();
        List<Object[]> resultList = em
                .createNamedStoredProcedureQuery(QnACategoryLevel.getMyPopularPostList)
                .setParameter("userId", id)
                .setParameter("color", _color)
                .getResultList();

        List postList = new ArrayList<ResponseBoardList>();
        for(Object o : resultList) {
            Object[] res = (Object[]) o;
            if(Integer.parseInt(res[3].toString())!=id) {
                BoardInteraction boardInteraction = null;
                if(Integer.parseInt(res[7].toString()) > 0 || Integer.parseInt(res[8].toString()) > 0){
                    QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findBySeq(Long.parseLong(res[0].toString()));

                    boardInteraction = boardInteractionRepository.findByBoardAndLikeUser(qnACategoryLevel,user)
                            .orElseGet(() -> BoardInteraction.builder()
                                    .board(qnACategoryLevel)
                                    .likeUser(user)
                                    .likeYn(0)
                                    .scrapYn(0)
                                    .build());
                }

                LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

                map.put("answerId", res[0]);
                map.put("color", res[1]);
                map.put("question", res[2]);
                map.put("userId", res[3]);
                map.put("nickname", res[4]);
                map.put("answer", res[5]);
                if(res[6].equals("N")) map.put("shareYN",false);
                else map.put("shareYN",true);
                map.put("level", res[7]);
                map.put("likes", res[8]);
                if(boardInteraction != null && boardInteraction.getLikeYn() == 1){
                    map.put("hasLiked", true);
                }
                else{
                    map.put("hasLiked", false);
                }
                map.put("scraps", res[9]);
                if(boardInteraction != null && boardInteraction.getScrapYn() == 1){
                    map.put("hasScrapped", true);
                }
                else{
                    map.put("hasScrapped", false);
                }
                map.put("comments", res[10]);
                map.put("regDate", res[11]);
                map.put("writtenDate", res[12]);

                postList.add(map);
            }
        }

        return postList;
    }

    public List getSearchList(int id, CharSequence keyword){

        List list = getLatestPost(id, -1);
        for(Iterator<LinkedHashMap<String, String>> it = list.iterator(); it.hasNext() ; ){
            LinkedHashMap<String, String> search = it.next();
            if(!search.get("answer").contains(keyword)){
                it.remove();
            }
        }
       return list;
    }
}
