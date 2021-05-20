package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.board.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnACommentRepository extends JpaRepository<BoardComment,Long> {
    int countByCategoryLevelId(long seq);

}
