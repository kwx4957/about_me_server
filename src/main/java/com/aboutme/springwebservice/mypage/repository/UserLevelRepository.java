package com.aboutme.springwebservice.mypage.repository;

import com.aboutme.springwebservice.mypage.model.UserLevelDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface UserLevelRepository extends JpaRepository<UserLevel, Long> {

//    ArrayList<UserLevel> findAllByUserIdOrderByColor(long userId);

    @Query(value = "SELECT * FROM User_Level WHERE user_id = :userId ORDER BY color ASC", nativeQuery = true)
    ArrayList<UserLevel> getProgressingByUserId(@Param(value="userId") long userId);

    @Query(value = "UPDATE User_Level SET level = :#{#userLevel.level}, experience = :#{#userLevel.experience} WHERE user_id = :#{#userLevel.user_id} AND color = :#{#userLevel.color}", nativeQuery = true)
    void updateUserLevelExperience(@Param(value="userLevel") UserLevel userLevel);

//    @Query(value = "SELECT * FROM qna_category_level qacl JOIN qna_category qac ON qacl.category_id = qac.seq JOIN default_enquery de ON de.seq = qac.title_id WHERE qac.author_id = :userId ORDER BY qacl.reg_date", nativeQuery = true)
//    ArrayList<> getMonthlyProgressingByUserId(@Param(value="userId") long userId);
//    @Query(value="SELECT user_id, color, level, experiece FROM User_Level ORDER BY color WHERE user_id = :user_id", nativeQuery = true)
//    List<UserLevelResponseVO> getAllProgressing(@Param(value="user_id") int user_id);
//
//    @Query(value="SELECT * FROM User_Level WHERE user_id = :user_id, color = :color", nativeQuery = true)
//    UserLevelResponseVO getProgressingByColor(@Param(value="user_id") int user_id, @Param(value="color") int color);
//
//    @Query(value="UPDATE User_Level SET level = :level, experience = :experience WHERE user_id = :user_id, color = :color", nativeQuery = true)
//    UserLevelResponseVO updateUserLevel(@Param(value="level") int level, @Param(value="experience") int experience, @Param(value="user_id") int user_id, @Param(value="color") int Color);
}