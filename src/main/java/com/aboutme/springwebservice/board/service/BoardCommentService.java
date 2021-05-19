package com.aboutme.springwebservice.board.service;

import com.aboutme.springwebservice.board.entity.BoardComment;
import com.aboutme.springwebservice.board.entity.QnACategory;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.model.CommentDTO;
import com.aboutme.springwebservice.board.model.response.ResponseComment;
import com.aboutme.springwebservice.board.repository.BoardCommentRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryRepository;
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
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

    @Transactional(readOnly = false)
    public ArrayList<CommentDTO> getCommentList(long categoryLevelId){
        List<BoardComment> commentList = boardCommentRepository.findByCategoryLevelIdOrderByRegDateAsc(categoryLevelId);

        ArrayList<CommentDTO> responseCommentList = new ArrayList<>();
        for(BoardComment commentObject : commentList) {
            long authorId = commentObject.getAuthorId();
            CommentDTO comment = new CommentDTO(commentObject);

            UserProfile user = userProfileRepository.findOneByUserID(authorId);

            comment.setNickname(user.getNickname());

            responseCommentList.add(comment);
        }

        return responseCommentList;
    }

    @Transactional(readOnly = false)
    public CommentDTO saveComment(CommentDTO comment) {

        BoardComment commentResult = boardCommentRepository.save(comment.toEntity());

        CommentDTO res = new CommentDTO(commentResult);
        UserProfile user = userProfileRepository.findOneByUserID(commentResult.getAuthorId());
        res.setNickname(user.getNickname());

        return res;
    }

    @Transactional(readOnly = true)
    public ResponseComment deleteComment(CommentDTO commentDTO, long userId) {
        BoardComment boardComment = boardCommentRepository.getOne(commentDTO.getCommentId());

        commentDTO = new CommentDTO(boardComment);

        QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findBySeq(commentDTO.getAnswerId());
        QnACategory qnACategory = qnACategoryRepository.findBySeq(qnACategoryLevel.getCategory_id());

        if (userId == qnACategory.getAuthor_id()) {
            boardCommentRepository.deleteById(boardComment.getSeq());

            return new ResponseComment(200, "Deleted", commentDTO);
        }
        return new ResponseComment(400, "다른 사람의 글에 있는 댓글은 삭제할 수 없습니다.", null);
    }
}
