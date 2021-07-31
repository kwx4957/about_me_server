package com.aboutme.springwebservice.message.service;

import com.aboutme.springwebservice.board.entity.BoardComment;
import com.aboutme.springwebservice.board.entity.DefaultEnquiry;
import com.aboutme.springwebservice.board.entity.QnACategory;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.repository.DefaultEnquiryRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryRepository;
import com.aboutme.springwebservice.board.repository.QnACommentRepository;
import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.entity.CommonResponse;
import com.aboutme.springwebservice.entity.ErrorResponse;
import com.aboutme.springwebservice.message.entity.DefaultReasonList;
import com.aboutme.springwebservice.message.entity.NotificationList;
import com.aboutme.springwebservice.message.entity.UserVoc;
import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import com.aboutme.springwebservice.message.model.SueJudgeVO;
import com.aboutme.springwebservice.message.model.SueVO;
import com.aboutme.springwebservice.message.model.response.ResponseSueList;
import com.aboutme.springwebservice.message.repository.NotificationRepository;
import com.aboutme.springwebservice.message.repository.SueRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SueService {

    private final SueRepository sueRepository;
    private final QnACategoryLevelRepository qnACategoryLevelRepository;
    private final QnACommentRepository qnACommentRepository;
    private final QnACategoryRepository qnACategoryRepository;
    private final UserProfileRepository userProfileRepository;
    private final DefaultEnquiryRepository defaultEnquiryRepository;
    private final NotificationRepository notificationRepository;
    private PushNotificationService pushNotificationService;

    @Transactional
    public ResponseEntity<?extends BasicResponse> sue(@RequestBody SueVO vo) {

        //임시Id
        UserInfo authorId = UserInfo.builder().seq(vo.getSuedUserId()).build();
        UserProfile color = UserProfile.UserProfileBuilder().userID(vo.getSuedUserId()).build();
        if(vo.getSueType().equals("board")){
            QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findById(vo.getTargetQuestionId())
                                                                          .orElseThrow(()-> new IllegalArgumentException("해당 글이 존재하지 않습니다"));
            //알림
            QnACategory qnACategory = qnACategoryRepository.findBySeq(qnACategoryLevel.getCategoryId());
            DefaultEnquiry defaultEnquiry = defaultEnquiryRepository.findBySeq(qnACategory.getTitleId());

            UserVoc userVoc = UserVoc.builder().authorId(authorId).questionId(qnACategoryLevel).reasonId(new DefaultReasonList(vo.getSueReason())).build();
            UserInfo userinfo = UserInfo.builder().seq(qnACategory.getAuthorId()).build();
            PushNotificationRequest request = PushNotificationRequest.builder()
                                                .message(defaultEnquiry.getQuestion()+"글에 신고가 들어왔어요.") //글작성자에게
                                                .title("오늘의나")
                                                .token("fWs7iOUjLkL5tExH0qq2Rl:APA91bFPh34RD63hy_6MZgVQ4nA927FKC6JjKgyoskBSnPBLgcWQSGXpPTsdLY7G8NvSRUTSA5VX4ummqzfF3UuFiA12mbXaJfJs7G6WEjGlR1tJs-LSBFiP5E4xl1Nca-orgDpTmnJ7")
                                                .topic("global").build();
            NotificationList noti = NotificationList.builder()
                                                    .message(request.getMessage())
                                                    .aulthorId(userinfo)
                                                    .color(color.getColor())
                                                    .build();
            notificationRepository.save(noti);
            pushNotificationService.sendPushNotificationToToken(request);
            sueRepository.save(userVoc);
            return  ResponseEntity.ok().body( new CommonResponse<>("신고가 접수되었습니다."));

        }else if(vo.getSueType().equals("comment")){

            BoardComment boardComment = qnACommentRepository.findById(vo.getTargetQuestionId())
                                                             .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다") );
            QnACategory qnACategory =qnACategoryRepository.findById(boardComment.getCategoryLevelId())
                                                            .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));

            if(qnACategory.getAuthorId() != authorId.getSeq()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse("글 작성자가 아닙니다.","403"));
            }
            qnACommentRepository.deleteById(vo.getTargetQuestionId());

            return  ResponseEntity.ok().body( new CommonResponse<>("댓글이 삭제되었습니다"));
        }else
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse("잘못된 입력입니다."));

    }

    @Transactional
    public ResponseEntity<?extends BasicResponse> sueList() {
        List<ResponseSueList> responseSueLists  = new ArrayList<>();
        List<UserVoc> userVoc;
        UserProfile authorId;
        UserProfile suedId;
        QnACategory qnACategory;
        QnACategoryLevel qnACategoryLevel;
        userVoc = sueRepository.findAll();

        if(userVoc.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse("신고된 글이 존재하지 않습니다."));
        }

        for(UserVoc userVoc1:userVoc) {
            qnACategory      = qnACategoryRepository.findBySeq(userVoc1.getQuestionId().getCategoryId());
            authorId         = userProfileRepository.findOneByUserID(userVoc1.getAuthorId().getSeq());
            suedId           = userProfileRepository.findOneByUserID(qnACategory.getAuthorId());
            qnACategoryLevel = qnACategoryLevelRepository.findBySeq(userVoc1.getAuthorId().getSeq());
            responseSueLists.add(ResponseSueList.builder().qnACategoryLevel(userVoc1.getQuestionId())
                                                          .sue(this.convertSue(authorId ,suedId ,userVoc1.getReasonId()))
                                                          .contents(qnACategoryLevel)
                                                          .build());
        }

       return ResponseEntity.ok().body( new CommonResponse<List>(responseSueLists));
   }

   @Transactional
   public ResponseEntity<?extends BasicResponse>  sueBoard(@RequestBody SueJudgeVO vo){

        if(vo.getSueReason().equals("delete")){
            qnACategoryLevelRepository.deleteById(vo.getBoardSeq()); //GlobalExceptionHander  EmptyResultDataAccessException
            return ResponseEntity.ok().body( new CommonResponse<>("신고 접수된 글이 삭제되었습니다."));

        }else if(vo.getSueReason().equals("reject")){
            sueRepository.deleteByquestionId(QnACategoryLevel.builder().seq(vo.getBoardSeq()).build());  //글이없는경우 예외처리
            return ResponseEntity.ok().body( new CommonResponse<>("신고 접수된 글이 반려되었습니다. "));

        }else{
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse("잘못된 입력입니다."));
        }
   }

   private String convertSue(UserProfile authorId, UserProfile suedId, DefaultReasonList reason ){
        String sueReason;
        sueReason= authorId.getNickname()+"님이 "+suedId.getNickname()+"님을 "+reason.getReason()+"내용으로 신고하였습니다";
        return sueReason;
   }

}


