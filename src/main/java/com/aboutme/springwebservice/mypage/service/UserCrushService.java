package com.aboutme.springwebservice.mypage.service;


import com.aboutme.springwebservice.board.entity.BoardInteraction;
import com.aboutme.springwebservice.board.entity.DefaultEnquiry;
import com.aboutme.springwebservice.board.entity.QnACategory;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.repository.*;
import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.entity.CommonResponse;
import com.aboutme.springwebservice.entity.ErrorResponse;
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
    private final BoardInteractionRepository boardInteractionRepository;
    private QnACategoryLevelRepository qnACategoryLevelRepository;
    private QnACommentRepository qnACommentRepository;
    private QnACategoryRepository qnACategoryRepository;
    private DefaultEnquiryRepository defaultEnquiryRepository;

    @Transactional
    public ResponseEntity<?extends BasicResponse>  crushLists(Long userId, String cursh){
        char likeYn = 'Y', scarpYn = 'Y';
        int commentCount = 0;
        List<ResponseCrushList> responseBoardSeq  = new ArrayList<>();
        List<ResponseCrushList> responseCrushList = new ArrayList<>();
        List<BoardInteraction> boardInteractions;
        QnACategoryLevel qnACategoryLevel;
        QnACategory qnACategory;
        DefaultEnquiry defaultEnquiry;

        //수정예정   코드 테스트용 유저 삽입
        UserInfo userInfo=UserInfo.builder().seq(userId).build();

        //input:1일 경우 string index out of 0 예외처리 할 예정  케이스 1: 값에 Null 이있어서 케이스 2 likes에 값이 있지만 scarp에 값이 없어서
        if(cursh.equals("likes")){
             boardInteractions =  boardInteractionRepository.findByLikeUserAndLikeYn(userInfo,likeYn);

             if(boardInteractions.isEmpty()){
                 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                      .body(new ErrorResponse("해당 유저의 좋아요 글이 없습니다"));
             }

            for(BoardInteraction boardInteraction : boardInteractions){
                responseBoardSeq.add(this.convertBoardSeq(boardInteraction));
            }

            for(int i=0; i<responseBoardSeq.size(); i++) {
                qnACategoryLevel = qnACategoryLevelRepository.findBySeq(responseBoardSeq.get(i).getBoardSeq());
                commentCount     = qnACommentRepository.countByCategoryLevelId(responseBoardSeq.get(i).getBoardSeq());
                qnACategory      = qnACategoryRepository.findBySeq(qnACategoryLevel.getCategory_id());
                defaultEnquiry   = defaultEnquiryRepository.findBySeq(qnACategory.getTitle_id());
                responseCrushList.add(this.convertList(qnACategoryLevel, commentCount,
                                                       convertColor(qnACategory.getColor()), defaultEnquiry.getQuestion()));
            }

        }else if(cursh.equals("scrap")){
             boardInteractions   = boardInteractionRepository.findByLikeUserAndScrapYn(userInfo, scarpYn);

            if(boardInteractions.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(new ErrorResponse("해당 유저의 스크랩 글이 없습니다"));
            }

            for(BoardInteraction boardInteraction : boardInteractions){
                responseBoardSeq.add(this.convertBoardSeq(boardInteraction));
            }

            for(int i=0; i<responseBoardSeq.size(); i++) {
                qnACategoryLevel = qnACategoryLevelRepository.findBySeq(responseBoardSeq.get(i).getBoardSeq());
                commentCount     = qnACommentRepository.countByCategoryLevelId(responseBoardSeq.get(i).getBoardSeq());
                qnACategory      = qnACategoryRepository.findBySeq(qnACategoryLevel.getCategory_id());
                defaultEnquiry   = defaultEnquiryRepository.findBySeq(qnACategory.getTitle_id());
                responseCrushList.add(this.convertList(qnACategoryLevel, commentCount,
                                                       convertColor(qnACategory.getColor()), defaultEnquiry.getQuestion()));
            }

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ErrorResponse("잘못된 접근입니다"));
        }

       return ResponseEntity.ok().body( new CommonResponse<List>(responseCrushList));
    }

    //좋아요 글번호추출
    private ResponseCrushList convertBoardSeq(BoardInteraction boardInteraction){
        return ResponseCrushList.builder().seq( boardInteraction.getBoard().getSeq() ).build();
    }

    //글번호로 리스트 변환
    private ResponseCrushList convertList(QnACategoryLevel qnACategoryLevel, int commentCount, String color, String question){
        return new ResponseCrushList(qnACategoryLevel, commentCount, color, question);
    }

    //색 문자열로 변환
    private String convertColor(int color){
        String Color="nothing";
        switch (color) {
            case 0: Color = "red"; break;
            case 1: Color = "yellow"; break;
            case 2: Color = "green"; break;
            case 3: Color = "pink"; break;
            case 4: Color = "purple"; break;
        }
        return Color;
    }
}
