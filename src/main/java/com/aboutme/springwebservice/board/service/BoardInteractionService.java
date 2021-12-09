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
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.entity.CommonResponse;
import com.aboutme.springwebservice.entity.ErrorResponse;
import com.aboutme.springwebservice.message.entity.NotificationList;
import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import com.aboutme.springwebservice.message.repository.NotificationRepository;
import com.aboutme.springwebservice.message.service.PushNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class BoardInteractionService {

    private final BoardInteractionRepository boardInteractionRepo;
    private final QnACategoryLevelRepository qnACategoryLevelRepo;
    private final QnACategoryRepository qnACategoryRepo;
    private final DefaultEnquiryRepository defaultEnquiryRepo;
    private final UserProfileRepository userProfileRepo;
    private final NotificationRepository notificationRepo;
    @Autowired
    private final PushNotificationService pushNotificationService;

    @Transactional
    public ResponseEntity<? extends BasicResponse> addLike(BoardInteractionVO vo) {

        UserProfile likeUser = userProfileRepo.findOneByUserID(vo.getUserId());

        QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepo.findById(vo.getQuestId())
                                                                .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));
        QnACategory qnACategory = qnACategoryRepo.findBySeq(qnACategoryLevel.getCategoryId());
        DefaultEnquiry defaultEnquiry = defaultEnquiryRepo.findBySeq(qnACategory.getTitleId());

        UserProfile authorUser = userProfileRepo.findOneByUserID(qnACategory.getAuthorId());

        if (likeUser.getUserID() == authorUser.getUserID()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("자신의 글에는 좋아요 할 수 없습니다", "400"));
        }

        BoardInteraction boardInteraction = boardInteractionRepo.findByBoardAndLikeUser(qnACategoryLevel, likeUser)
                                                                .orElseGet(() -> BoardInteraction.builder()
                                                                                .board(qnACategoryLevel)
                                                                                .likeUser(likeUser)
                                                                                .likeYn(0)
                                                                                .authorId(authorUser)
                                                                                .build());

        if (boardInteraction.getLikeYn() == 0) {
            PushNotificationRequest request = PushNotificationRequest.builder()
                    .message(likeUser.getNickname() + "님이 " + defaultEnquiry.getQuestion() + "에 공감해주었어요.")
                    .title("오늘의나").token(authorUser.getFcmToken()).topic("global").build();

            boardInteraction.likeYes();
            boardInteraction.getBoard().addLikesCount();
            Map<String, String> data = new HashMap<>();

            if (authorUser.getPush_yn().equals('Y')) {
                pushNotificationService.sendPushNotificationToTokenWithData(data, request);
            }
            notificationRepo.save(NotificationList.builder()
                    .message(request.getMessage())
                    .color(likeUser.getColor())
                    .aulthorId(authorUser)
                    .build());
            boardInteractionRepo.save(boardInteraction);
            return ResponseEntity.ok().body(new CommonResponse<>());
        } else if (boardInteraction.getLikeYn() == 1) {
            boardInteraction.likeNo();
            boardInteraction.getBoard().subtractLikes();
            boardInteractionRepo.save(boardInteraction);
            return ResponseEntity.ok().body(new CommonResponse<>());
        } else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("좋아요를 처리하지 못하였습니다", "500"));
    }

    @Transactional
    public ResponseEntity<? extends BasicResponse> addScrap(BoardInteractionVO vo) {

        UserProfile likeUser = UserProfile.UserProfileBuilder().userID(vo.getUserId()).build();
        QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepo.findById(vo.getQuestId())
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));

        QnACategory qnACategory = qnACategoryRepo.findBySeq(qnACategoryLevel.getCategoryId());
        UserProfile authorUser = UserProfile.UserProfileBuilder().userID(qnACategory.getAuthorId()).build();

        if (likeUser.getUserID() == authorUser.getUserID()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("자신의 글에는 스크랩 할 수 없습니다", "400"));
        }
        BoardInteraction boardInteraction = boardInteractionRepo.findByBoardAndLikeUser(qnACategoryLevel, likeUser)
                .orElseGet(() -> new BoardInteraction(qnACategoryLevel, likeUser, authorUser, 0));

        if (boardInteraction.getScrapYn() == 0) {
            boardInteraction.scrapYes();
            boardInteraction.getBoard().addScrapCount();
            boardInteractionRepo.save(boardInteraction);
            return ResponseEntity.ok().body(new CommonResponse<>());
        } else if (boardInteraction.getScrapYn() == 1) {
            boardInteraction.scrapNo();
            boardInteraction.getBoard().subtractScrap();
            boardInteractionRepo.save(boardInteraction);
            return ResponseEntity.ok().body(new CommonResponse<>());
        } else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("스크랩을 처리하지 못하였습니다", "500"));
    }

    @Transactional
    public ResponseEntity<? extends BasicResponse> editIsShare(long cardSeq, int level) {
        QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepo.findById(cardSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));

        qnACategoryLevelRepo.updateCardIsShare(cardSeq, level);
        return ResponseEntity.ok().body(new CommonResponse<>());

    }

}
