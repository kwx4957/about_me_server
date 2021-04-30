package com.aboutme.springwebservice.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryQuesRepository extends JpaRepository<CategoryQuestion,String> {
    @Transactional
    @Query(value = "insert into aboutme_rds.QnA_Category(author_id,title_id,color) values(?1,?2,?3)",nativeQuery = true)
    void saveDaily(int userId, int title, int color);

    @Transactional
    @Query(value = "select seq from aboutme_rds.QnA_Category where author_id = ?1 and title_id = ?2 and color = ?3",nativeQuery = true)
    CategoryQuestion storedDaily(int userId, int title, int color);
}
