package com.aboutme.springwebservice.mypage.service;


import com.aboutme.springwebservice.board.entity.BoardInteraction;
import com.aboutme.springwebservice.board.entity.DefaultEnquiry;
import com.aboutme.springwebservice.board.entity.QnACategory;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.repository.*;
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.entity.ErrorResponse;
import com.aboutme.springwebservice.entity.ListCommonResponse;
import com.aboutme.springwebservice.mypage.model.response.ResponseCrushList;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserCrushService {
    private final BoardInteractionRepository boardInteractionRepo;
    private final QnACategoryLevelRepository qnACategoryLevelRepo;
    private final QnACommentRepository qnACommentRepo;
    private final QnACategoryRepository qnACategoryRepo;
    private final DefaultEnquiryRepository defaultEnquiryRepo;

    @Transactional
    public ResponseEntity<? extends BasicResponse> crushLists(Long userId, String crush, int color) {
        int Yes = 1;
        int commentCount;
        List<ResponseCrushList> responseBoardSeq = new ArrayList<>();
        List<ResponseCrushList> responseCrushList = new ArrayList<>();
        List<BoardInteraction> boardInteractions;
        QnACategoryLevel qnACategoryLevel;
        QnACategory qnACategory;
        DefaultEnquiry defaultEnquiry;
        UserProfile user = UserProfile.UserProfileBuilder().userID(userId).build();

        if (crush.equals("likes")) {
            boardInteractions = boardInteractionRepo.findByLikeUserAndLikeYn(user, Yes);
        }else if(crush.equals("scrap")) {
            boardInteractions = boardInteractionRepo.findByLikeUserAndScrapYn(user, Yes);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("잘못된 접근입니다"));
        }

        for (BoardInteraction boardInteraction : boardInteractions) {
            responseBoardSeq.add(this.convertBoardSeq(boardInteraction));
        }

        for (ResponseCrushList resSeq : responseBoardSeq) {
            qnACategoryLevel = qnACategoryLevelRepo.findBySeq(resSeq.getBoardSeq());
            commentCount = qnACommentRepo.countByCategoryLevelId(resSeq.getBoardSeq());
            qnACategory = qnACategoryRepo.findBySeq(qnACategoryLevel.getCategoryId());
            defaultEnquiry = defaultEnquiryRepo.findBySeq(qnACategory.getTitleId());
            if (qnACategory.getColor() == color || color == -1) {
                responseCrushList.add(this.convertList(qnACategoryLevel, commentCount,
                        qnACategory.getColor(), defaultEnquiry.getQuestion(), resSeq));
            }
        }

        return ResponseEntity.ok().body(new ListCommonResponse<ResponseCrushList>(responseCrushList));
    }

    //좋아요 글번호추출
    private ResponseCrushList convertBoardSeq(BoardInteraction boardInteraction) {
        return ResponseCrushList.builder()
                .seq(boardInteraction.getBoard().getSeq())
                .hasliked(boardInteraction.getLikeYn())
                .hasscraped(boardInteraction.getScrapYn())
                .build();
    }

    //글번호로 리스트 변환
    private ResponseCrushList convertList(QnACategoryLevel qnACategoryLevel, int commentCount,
                                          int color, String question, ResponseCrushList responseCrushList) {
        return new ResponseCrushList(qnACategoryLevel, commentCount, color, question, responseCrushList);
    }
}
