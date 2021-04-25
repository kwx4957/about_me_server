package com.aboutme.springwebservice.mypage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SelfQuestRepository extends JpaRepository<SelfQuest,String> {

    @Query(value = "delete from aboutme_rds.QnA_SelfQuest where author_id = ?1 and p_seq = ?2 and theme = ?3",nativeQuery = true)
        void deleteTheme(int userId, int stage, String theme);
}
