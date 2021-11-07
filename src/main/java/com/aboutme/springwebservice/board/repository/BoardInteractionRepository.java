package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.board.entity.BoardInteraction;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardInteractionRepository extends JpaRepository<BoardInteraction,Long>{
    Optional<BoardInteraction> findByBoardAndLikeUser(QnACategoryLevel qnACategoryLevel, UserProfile userInfo);

    List<BoardInteraction> findByLikeUserAndLikeYn(UserInfo userId, int likeYn);

    List<BoardInteraction> findByLikeUserAndScrapYn(UserInfo userId, int scarpYn);
}
