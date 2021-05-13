package com.aboutme.springwebservice.board.service;

import com.aboutme.springwebservice.board.entity.BoardInteraction;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.repository.BoardInteractionRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.domain.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class BoardInteractionService {

        private  BoardInteractionRepository boardInteractionRepository;
        private  QnACategoryLevelRepository qnACategoryLevelRepository;

        @Transactional //자기글 좋아요 X 글없을경우 에러 표시
        public boolean addLike(long userId, Long seq) {
                //임시 userId;
                UserInfo likeUser= UserInfo.builder().seq(userId).build();
                QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findById(seq).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));

                BoardInteraction boardInteraction = boardInteractionRepository.findByBoardAndLikeUser(qnACategoryLevel,likeUser)
                                                                              .orElseGet(()-> BoardInteraction.builder()
                                                                                             .board(qnACategoryLevel)
                                                                                             .likeUser(likeUser)
                                                                                             .likeYn(0)
                                                                                             .authorId(likeUser)
                                                                                             .build());

                if(boardInteraction.getLikeYn() == 0){
                    boardInteraction.likeYes();
                    boardInteraction.getBoard().addLikesCount();
                    boardInteractionRepository.save(boardInteraction);
                    return true;
                }
                else if(boardInteraction.getLikeYn() == 1){
                    boardInteraction.likeNo();
                    boardInteraction.getBoard().subtractLikes();
                    boardInteractionRepository.save(boardInteraction);
                    return true;
                }
                else
                    return false;
        }
        @Transactional //자기글 좋아요 X 글없을경우 에러 표시
        public boolean addScrap(long userId, Long seq) {
                UserInfo likeUser= UserInfo.builder().seq(userId).build();
                QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findById(seq).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));

                BoardInteraction boardInteraction = boardInteractionRepository.findByBoardAndLikeUser(qnACategoryLevel,likeUser)
                                                                              .orElseGet(()->new BoardInteraction(qnACategoryLevel,likeUser,likeUser,0));

                if(boardInteraction.getScrapYn() == 0){
                    boardInteraction.scrapYes();
                    boardInteraction.getBoard().addScrapCount();
                    boardInteractionRepository.save(boardInteraction);
                    return true;
                }
                else if(boardInteraction.getScrapYn() == 1){
                    boardInteraction.scrapNo();
                    boardInteraction.getBoard().subtractScrap();
                    boardInteractionRepository.save(boardInteraction);
                    return true;
                }
                else
                    return false;
        }
}
