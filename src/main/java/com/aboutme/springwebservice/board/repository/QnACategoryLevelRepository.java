package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QnACategoryLevelRepository extends JpaRepository<QnACategoryLevel, Long> {

    @Query(value = "DELETE FROM QnA_Category_Level WHERE category_id = ?1", nativeQuery = true)
    void delCardAnswer(int categorySeq);

    QnACategoryLevel findBySeq(long seq);
}
