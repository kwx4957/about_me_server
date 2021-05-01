package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.board.model.DailyAnswerDTO;
import com.aboutme.springwebservice.mypage.repository.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QnACategoryLevelRepository extends JpaRepository<QnACategoryLevel, Long> {

    @Query(value = "DELETE FROM QnA_Category_Level WHERE category_id = ?1", nativeQuery = true)
    void delCardAnswer(int categorySeq);
}
