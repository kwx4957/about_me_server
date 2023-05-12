package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.board.entity.QnACategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnACategoryRepository extends JpaRepository<QnACategory, Long> {

    @Query(value = "DELETE FROM QnA_Category WHERE seq = ?1", nativeQuery = true)
    void delCardQuestion(long categorySeq);

    QnACategory findBySeq(long seq);

//    QnACategory findByAuthorIdAndTitleId(long userId, long questId);

    @Query(value="SELECT * FROM QnA_Category WHERE author_id=?1 AND color=?2", nativeQuery = true)
    List<QnACategory> findByAuthorIdAndColor(long userId, int color);
}
