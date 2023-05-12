package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.board.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QnACommentRepository extends JpaRepository<BoardComment,Long> {
    int countByCategoryLevelId(long seq);
    @Query(value = "DELETE FROM QnA_Comment WHERE category_level_id = ?1", nativeQuery = true)
    void delCardComment(long catgLevelSeq);
}
