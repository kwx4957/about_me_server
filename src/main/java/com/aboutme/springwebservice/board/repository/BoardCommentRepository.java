package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.board.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
    List<BoardComment> findByCategoryLevelIdOrderByRegDateAsc(long categoryLevelId);

    @Modifying
    @Query(value = "DELETE FROM QnA_Comment WHERE seq = :commentId", nativeQuery = true)
    void deleteById(@Param(value="commentId") Long commentId);

}
