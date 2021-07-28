package com.aboutme.springwebservice.board.service;

import com.aboutme.springwebservice.board.entity.BoardInteraction;
import com.aboutme.springwebservice.board.entity.DefaultEnquiry;
import com.aboutme.springwebservice.board.entity.QnACategory;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.model.BoardInteractionVO;
import com.aboutme.springwebservice.board.repository.BoardInteractionRepository;
import com.aboutme.springwebservice.board.repository.DefaultEnquiryRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryRepository;
import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.entity.CommonResponse;
import com.aboutme.springwebservice.entity.ErrorResponse;
import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import com.aboutme.springwebservice.message.service.PushNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class BoardInteractionService {

        private final BoardInteractionRepository boardInteractionRepository;
        private final QnACategoryLevelRepository qnACategoryLevelRepository;
        private final QnACategoryRepository qnACategoryRepository;
        private final DefaultEnquiryRepository defaultEnquiryRepository;
        private final UserProfileRepository userProfileRepository;
        private PushNotificationService pushNotificationService;


        @Transactional
        public ResponseEntity<?extends BasicResponse> addLike(BoardInteractionVO vo) {
                //임시 userId;
                UserInfo likeUser = UserInfo.builder().seq(vo.getUserId()).build();
                UserProfile nickname = userProfileRepository.findOneByUserID(vo.getUserId());

                QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findById(vo.getQuestId())
                                                                              .orElseThrow(()-> new IllegalArgumentException("해당 글이 존재하지 않습니다"));
                QnACategory qnACategory = qnACategoryRepository.findBySeq(qnACategoryLevel.getCategoryId());
                DefaultEnquiry defaultEnquiry = defaultEnquiryRepository.findBySeq(qnACategory.getTitleId());

                UserInfo authorUser= UserInfo.builder().seq(qnACategory.getAuthorId()).build();
                if( likeUser.getSeq() == authorUser.getSeq() ){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                         .body(new ErrorResponse("자신의 글에는 좋아요 할 수 없습니다","400"));
                }
                BoardInteraction boardInteraction = boardInteractionRepository.findByBoardAndLikeUser(qnACategoryLevel,likeUser)
                                                                              .orElseGet(()-> BoardInteraction.builder()
                                                                                             .board(qnACategoryLevel)
                                                                                             .likeUser(likeUser)
                                                                                             .likeYn(0)
                                                                                             .authorId(authorUser)
                                                                                             .build());
                if(boardInteraction.getLikeYn() == 0){
                    PushNotificationRequest request = PushNotificationRequest.builder()
                                                                            .message(nickname.getNickname()+"님이 "+defaultEnquiry.getQuestion()+"에 공감해주었어요.")
                                                                            .title("오늘의나")
                                                                            .token("fWs7iOUjLkL5tExH0qq2Rl:APA91bFPh34RD63hy_6MZgVQ4nA927FKC6JjKgyoskBSnPBLgcWQSGXpPTsdLY7G8NvSRUTSA5VX4ummqzfF3UuFiA12mbXaJfJs7G6WEjGlR1tJs-LSBFiP5E4xl1Nca-orgDpTmnJ7")
                                                                            .topic("global").build();
                    boardInteraction.likeYes();
                    boardInteraction.getBoard().addLikesCount();
                    boardInteractionRepository.save(boardInteraction);
                    pushNotificationService.sendPushNotificationToToken(request);
                    return ResponseEntity.ok().body( new CommonResponse<>());
                }
                else if(boardInteraction.getLikeYn() == 1){
                    boardInteraction.likeNo();
                    boardInteraction.getBoard().subtractLikes();
                    boardInteractionRepository.save(boardInteraction);
                    return ResponseEntity.ok().body( new CommonResponse<>());
                }
                else
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                         .body(new ErrorResponse("좋아요를 처리하지 못하였습니다","500"));
        }

        @Transactional
        public ResponseEntity<?extends BasicResponse> addScrap(BoardInteractionVO vo) {

                UserInfo likeUser= UserInfo.builder().seq(vo.getUserId()).build();
                QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findById(vo.getQuestId())
                                                                              .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));
                QnACategory qnACategory = qnACategoryRepository.findBySeq(qnACategoryLevel.getCategoryId());
                UserInfo authorUser= UserInfo.builder().seq(qnACategory.getAuthorId()).build();
                if(likeUser.getSeq() == authorUser.getSeq() ){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                         .body(new ErrorResponse("자신의 글에는 스크랩 할 수 없습니다","400"));
                }
                BoardInteraction boardInteraction = boardInteractionRepository.findByBoardAndLikeUser(qnACategoryLevel,likeUser)
                                                                              .orElseGet(()->new BoardInteraction(qnACategoryLevel,likeUser,authorUser,0));

                if(boardInteraction.getScrapYn() == 0){
                    boardInteraction.scrapYes();
                    boardInteraction.getBoard().addScrapCount();
                    boardInteractionRepository.save(boardInteraction);
                    return ResponseEntity.ok().body( new CommonResponse<>());
                }
                else if(boardInteraction.getScrapYn() == 1){
                    boardInteraction.scrapNo();
                    boardInteraction.getBoard().subtractScrap();
                    boardInteractionRepository.save(boardInteraction);
                    return ResponseEntity.ok().body( new CommonResponse<>());
                }
                else
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                         .body(new ErrorResponse("스크랩을 처리하지 못하였습니다","500"));
        }

    @Transactional
    public ResponseEntity<?extends BasicResponse> editIsShare(long cardSeq, int level) {
        QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findById(cardSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));

        qnACategoryLevelRepository.updateCardIsShare(cardSeq,level);
        return ResponseEntity.ok().body( new CommonResponse<>());

    }

}
