package com.aboutme.springwebservice.board.service;

import com.aboutme.springwebservice.board.entity.BoardInteraction;
import com.aboutme.springwebservice.board.entity.DefaultEnquiry;
import com.aboutme.springwebservice.board.entity.QnACategory;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.model.response.ResponseBoardList;
import com.aboutme.springwebservice.board.repository.BoardInteractionRepository;
import com.aboutme.springwebservice.board.repository.DefaultEnquiryRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryRepository;
import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.mypage.repository.UserLevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardInfoService {

    private QnACategoryLevelRepository qnACategoryLevelRepository;
    private BoardInteractionRepository boardInteractionRepository;
    private QnACategoryRepository qnACategoryRepository;
    private UserLevelRepository userLevelRepository;
    private DefaultEnquiryRepository defaultEnquiryRepository;

    @PersistenceContext
    EntityManager em;

    public Object getPost(Long id, Long answerId) {
        UserInfo user= UserInfo.builder().seq(id).build();
        List<Object[]> resultList = em
                .createNamedStoredProcedureQuery(QnACategoryLevel.getPost)
                .setParameter("answerId", answerId)
                .getResultList();

        BoardInteraction boardInteraction = null;
        // 좋아요 또는 스크랩한 기록이 있을 때
        if(Integer.parseInt(resultList.get(0)[8].toString()) > 0 || Integer.parseInt(resultList.get(0)[9].toString()) > 0){
            QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findBySeq(Long.parseLong(resultList.get(0)[0].toString()));

            boardInteraction = boardInteractionRepository.findByBoardAndLikeUser(qnACategoryLevel,user)
                    .orElseGet(() -> BoardInteraction.builder()
                            .board(qnACategoryLevel)
                            .likeUser(user)
                            .likeYn(0)
                            .scrapYn(0)
                            .build());
        }

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
        if(boardInteraction != null && boardInteraction.getLikeYn() == 1){
            map.put("hasLiked", true);
        }
        else{
            map.put("hasLiked", false);
        }
        map.put("scraps", resultList.get(0)[8]);
        if(boardInteraction != null && boardInteraction.getScrapYn() == 1){
            map.put("hasScrapped", true);
        }
        else{
            map.put("hasScrapped", false);
        }
        map.put("comments", resultList.get(0)[10]);
        map.put("regDate", resultList.get(0)[11]);
        map.put("writtenDate", resultList.get(0)[12]);

        return map;
    }

    public List getPastResponse(Long answerId) {
        List postList = new ArrayList<ResponseBoardList>();
        QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findBySeq(answerId);

        Long categoryId = qnACategoryLevel.getCategoryId();

        List<QnACategoryLevel> qnACategoryList = qnACategoryLevelRepository.findByCategoryIdOrderByLevelDesc(categoryId);

        for(QnACategoryLevel q : qnACategoryList) {
            if(q.getSeq() >= answerId) { // 해당 글을 제외
                continue;
            }
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

            QnACategory qnACategory = qnACategoryRepository.findBySeq(q.getCategoryId());
            DefaultEnquiry defaultEnquiry = defaultEnquiryRepository.findBySeq(qnACategory.getTitle_id());

            map.put("answerId", q.getSeq());
            map.put("question", defaultEnquiry.getQuestion());
            switch(defaultEnquiry.getColor()) {
                case 0:
                    map.put("color", "red");
                    break;
                case 1:
                    map.put("color", "yellow");
                    break;
                case 2:
                    map.put("color", "green");
                    break;
                case 3:
                    map.put("color", "pink");
                    break;
                case 4:
                    map.put("color", "purple");
                    break;
            }
            map.put("answer", q.getAnswer());
            map.put("level", q.getLevel());

            postList.add(map);
        }

        return postList;
    }
}
