package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.board.entity.QnACategory;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QnACategoryLevelRepository extends JpaRepository<QnACategoryLevel, Long> {

    @Query(value = "DELETE FROM QnA_Category_Level WHERE seq = ?1", nativeQuery = true)
    void delCardAnswer(long categorySeq);

    @Query(value = "SELECT *  FROM QnA_Category_Level WHERE date_format(reg_date,'%Y-%m-%d') = CURDATE() ", nativeQuery = true)
    List<QnACategoryLevel> selectTodate();

    @Query(value = "UPDATE QnA_Category_Level SET  share_yn = CASE WHEN share_yn ='Y' THEN 'N' ELSE 'Y' END WHERE seq = ?1 AND level = ?2", nativeQuery = true)
    void updateCardIsShare(long categorySeq , int level);

    QnACategoryLevel findBySeq(long seq);

    List<QnACategoryLevel> findByCategoryId(Long categoryId);

    List<QnACategoryLevel> findByCategoryIdOrderByLevelDesc(long seq);
}
