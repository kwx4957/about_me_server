package com.aboutme.springwebservice.board.service;

import com.aboutme.springwebservice.board.entity.BoardComment;
import com.aboutme.springwebservice.board.entity.DefaultEnquiry;
import com.aboutme.springwebservice.board.entity.QnACategory;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.model.CommentDTO;
import com.aboutme.springwebservice.board.model.response.ResponseComment;
import com.aboutme.springwebservice.board.repository.BoardCommentRepository;
import com.aboutme.springwebservice.board.repository.DefaultEnquiryRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryRepository;
import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import com.aboutme.springwebservice.message.entity.NotificationList;
import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import com.aboutme.springwebservice.message.repository.NotificationRepository;
import com.aboutme.springwebservice.message.service.PushNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardCommentService {

    private BoardCommentRepository boardCommentRepository;
    private UserProfileRepository userProfileRepository;
    private QnACategoryLevelRepository qnACategoryLevelRepository;
    private QnACategoryRepository qnACategoryRepository;
    private DefaultEnquiryRepository defaultEnquiryRepository;
    private PushNotificationService pushNotificationService;
    private NotificationRepository notificationRepository;

    @Transactional(readOnly = false)
    public ArrayList<CommentDTO> getCommentList(long categoryLevelId){
        List<BoardComment> commentList = boardCommentRepository.findByCategoryLevelIdOrderByRegDateAsc(categoryLevelId);

        ArrayList<CommentDTO> responseCommentList = new ArrayList<>();
        for(BoardComment commentObject : commentList) {
            long authorId = commentObject.getAuthorId();
            CommentDTO comment = new CommentDTO(commentObject);

            UserProfile user = userProfileRepository.findOneByUserID(authorId);

            comment.setNickname(user.getNickname());
            switch(user.getColor()){
                case 0:
                    comment.setColor("red");
                    break;
                case 1:
                    comment.setColor("yellow");
                    break;
                case 2:
                    comment.setColor("green");
                    break;
                case 3:
                    comment.setColor("pink");
                    break;
                case 4:
                    comment.setColor("purple");
                    break;
            }

            responseCommentList.add(comment);
        }

        return responseCommentList;
    }

    @Transactional(readOnly = false)
    public CommentDTO saveComment(CommentDTO comment) {

        BoardComment commentResult = boardCommentRepository.save(comment.toEntity());

        CommentDTO res = new CommentDTO(commentResult);
        UserProfile user = userProfileRepository.findOneByUserID(commentResult.getAuthorId());
        UserInfo notiId = UserInfo.builder().seq(user.getUserID()).build();
        res.setNickname(user.getNickname());

        QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findBySeq(res.getAnswerId());
        QnACategory qnACategory = qnACategoryRepository.findBySeq(qnACategoryLevel.getCategoryId());
        DefaultEnquiry title = defaultEnquiryRepository.findBySeq(qnACategory.getTitleId());
        PushNotificationRequest request = PushNotificationRequest.builder()
                                          .message(user.getNickname()+" "+title.getQuestion() +"에 댓글을 남겼습니다") //글 작성자에게 알림
                                          .title("오늘의나")
                                          .token("fWs7iOUjLkL5tExH0qq2Rl:APA91bFPh34RD63hy_6MZgVQ4nA927FKC6JjKgyoskBSnPBLgcWQSGXpPTsdLY7G8NvSRUTSA5VX4ummqzfF3UuFiA12mbXaJfJs7G6WEjGlR1tJs-LSBFiP5E4xl1Nca-orgDpTmnJ7")
                                          .topic("global").build();
        NotificationList noti = NotificationList.builder()
                                                .message(request.getMessage())
                                                .color(user.getColor())
                                                .aulthorId(notiId)
                                                .build();
        notificationRepository.save(noti);
        pushNotificationService.sendPushNotificationToToken(request);


        return res;
    }

    @Transactional(readOnly = true)
    public ResponseComment deleteComment(CommentDTO commentDTO, long userId) {
        BoardComment boardComment = boardCommentRepository.getOne(commentDTO.getCommentId());

        commentDTO = new CommentDTO(boardComment);

        if (commentDTO.getAuthorId() == userId) {
            boardCommentRepository.deleteById(boardComment.getSeq());

            return new ResponseComment(200, "Deleted", commentDTO);
        }
        return new ResponseComment(400, "다른 사람의 댓글은 삭제할 수 없습니다.", null);
    }
}
