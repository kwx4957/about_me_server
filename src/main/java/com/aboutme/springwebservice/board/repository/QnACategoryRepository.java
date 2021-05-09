package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.board.entity.QnACategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QnACategoryRepository extends JpaRepository<QnACategory, Long> {

    @Query(value = "DELETE FROM QnA_Category WHERE seq = ?1", nativeQuery = true)
    void delCardQuestion(int categorySeq);
    QnACategory findBySeq(long seq);
}
