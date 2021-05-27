package com.aboutme.springwebservice.message.service;

import com.aboutme.springwebservice.board.entity.QnACategory;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryRepository;
import com.aboutme.springwebservice.board.repository.QnACommentRepository;
import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.entity.CommonResponse;
import com.aboutme.springwebservice.entity.ErrorResponse;
import com.aboutme.springwebservice.message.entity.DefaultReasonList;
import com.aboutme.springwebservice.message.entity.UserVoc;
import com.aboutme.springwebservice.message.model.SueVO;
import com.aboutme.springwebservice.message.repository.SueRepository;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SueService {

    private final SueRepository sueRepository;
    private final QnACategoryLevelRepository qnACategoryLevelRepository;
    private final QnACommentRepository qnACommentRepository;
    private final QnACategoryRepository qnACategoryRepository;

    @Transactional
    public ResponseEntity<?extends BasicResponse> sue(@RequestBody SueVO vo) {

        QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findById(vo.getTargetQuestionId())
                                                                      .orElseThrow(()-> new IllegalArgumentException("해당 글이 존재하지 않습니다"));
        //임시Id
        UserInfo authorId = UserInfo.builder().seq(vo.getSuedUserId()).build();

        if(vo.getSueType().equals("board")){

            UserVoc userVoc= UserVoc.builder().authorId(authorId).questionId(qnACategoryLevel).reasonId(new DefaultReasonList(vo.getSueReason())).build();
            sueRepository.save(userVoc);
            return  ResponseEntity.ok().body( new CommonResponse<>("신고가 접수되었습니다."));

        }else if(vo.getSueType().equals("comment")){

            Optional<QnACategory> qnACategory=qnACategoryRepository.findById(qnACategoryLevel.getCategoryId());
            qnACategory.orElseThrow(()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));

            if(qnACategory.get().getAuthor_id()!=authorId.getSeq()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse("글 작성자가 아닙니다.","403"));
            }

            qnACommentRepository.deleteById(vo.getTargetQuestionId());
            return  ResponseEntity.ok().body( new CommonResponse<>());
        }else
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse("잘못된 입력입니다."));

    }

//    @Transactional
//    public List<SueVO> sueList(){
//        //닉네임+닉네임 +내용으로 신고하엿습니다
//        List<SueVO> suevo  = new ArrayList<>();
//
//        suevo.add(sueRepository.findAll(
//
//        ));
//        retrun
//    }
//
//    private SueVO convertSueVo(UserVoc userVoc){
//        return SueVO. build();
//    }

}
