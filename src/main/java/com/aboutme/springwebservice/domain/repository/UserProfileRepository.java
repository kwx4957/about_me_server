package com.aboutme.springwebservice.domain.repository;

import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.mypage.entity.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query(value = "UPDATE User_Profile SET color = ?1 WHERE user_id = ?2", nativeQuery = true)
    void updateUserColor(int color , long user);

    List<UserProfile> findByUserID(long authorId);

    UserProfile findOneByUserID(long authorId);
}
