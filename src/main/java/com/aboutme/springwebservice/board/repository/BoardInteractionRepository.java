package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.board.entity.BoardInteraction;
import com.aboutme.springwebservice.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardInteractionRepository extends JpaRepository<BoardInteraction,Long>{
    Optional<BoardInteraction> findByBoardAndLikeUser(QnACategoryLevel qnACategoryLevel, UserInfo userInfo);

    List<BoardInteraction> findByLikeUserAndLikeYn(UserInfo userId, char likeYn);

    List<BoardInteraction> findByLikeUserAndScrapYn(UserInfo userId, char scarpYn);
}
