package com.aboutme.springwebservice.board.service;

import com.aboutme.springwebservice.board.entity.BoardInteraction;
import com.aboutme.springwebservice.board.repository.BoardInteractionRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryLevel;
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

        @Transactional //좋아요 삽입 및 수정 총 3N
        public boolean addLike(long userId, Long seq) {

                UserInfo likeUser= UserInfo.builder().seq(userId).build();
                QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findById(seq).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));
                //값이 존재하면 기존값 이용 없다면 새로운 객체 생성
                BoardInteraction boardInteraction = boardInteractionRepository.findByBoardAndLikeUser(qnACategoryLevel,likeUser).orElseGet(() -> BoardInteraction.builder()
                                                                                                                                  .board(qnACategoryLevel)
                                                                                                                                  .likeUser(likeUser)
                                                                                                                                  .likeYn('Y')
                                                                                                                                  .authorId(likeUser)
                                                                                                                                  .build());
                if(boardInteraction.getLikeYn() == 'N'){
                    boardInteraction.likeYes();
                    boardInteractionRepository.save(boardInteraction);
                    return false;
                }
                else if(boardInteraction.getLikeYn() == 'Y'){
                    boardInteraction.likeNo();
                    boardInteractionRepository.save(boardInteraction);
                    return false;
                }
                else
                    return false;
        }
        @Transactional
        public int countLikes(Long seq){ //+1 or -1으로할지 해당글 조회로 최신화활지
               //BoardInteraction boardInteractionDTO =boardInteractionRepository.findById(seq);
               //countby 조희 결과수 출력
               //글정보 + likeYn Y 값 추출
                int count=0;
                return  count;
        }


}
