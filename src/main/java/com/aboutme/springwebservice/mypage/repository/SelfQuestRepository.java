package com.aboutme.springwebservice.mypage.repository;

import com.aboutme.springwebservice.mypage.entity.SelfQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SelfQuestRepository extends JpaRepository<SelfQuest,String> {

    @Query(value = "delete from aboutme_rds.QnA_SelfQuest where author_id = ?1 and p_seq = ?2 and theme = ?3",nativeQuery = true)
        void deleteTheme(int userId, int stage, String theme);
    @Query(value = "update QnA_SelfQuest set theme='?1', update_date=now() where author_id =?2 and theme = ?3  and p_seq = ?4",nativeQuery = true)
        void updateTitle(String _new, int user , String _theme, int stages);
}
